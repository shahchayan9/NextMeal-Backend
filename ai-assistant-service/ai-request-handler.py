from flask import Flask, request, jsonify
from flask_cors import CORS
from opensearchpy import OpenSearch
import openai
import os
from dotenv import load_dotenv

load_dotenv()

OPEN_API_KEY = os.getenv("OPEN_API_KEY")
OPEN_SEARCH_USER = os.getenv("OPEN_SEARCH_USER")
OPEN_SEARCH_PWD = os.getenv("OPEN_SEARCH_PWD")
RELEVANCE_THRESHOLD = 0.7

openai.api_key = OPEN_API_KEY

opensearch = OpenSearch(
    hosts=["https://search-ai-assistant-domain-pjwpl7u47jgmall7hm2qcvjopu.us-west-1.es.amazonaws.com"],
    http_auth=(OPEN_SEARCH_USER, OPEN_SEARCH_PWD),
    use_ssl=True,
    verify_certs=True
)

app = Flask(__name__)
CORS(app)

def check_relevance(query_embedding, index="business-embeddings"):
    search_body = {
        "size": 5,
        "query": {
            "knn": {
                "embedding": {
                    "vector": query_embedding,
                    "k": 5
                }
            }
        }
    }
    
    response = opensearch.search(index=index, body=search_body)
    results = response["hits"]["hits"]

    if results and results[0]["_score"] > RELEVANCE_THRESHOLD:
        return results
    return None


def generate_response(results, query):
    context = "\n".join(
        [f"- {hit['_source']['name']}, {hit['_source']['city']}, {hit['_source']['state']}: {hit['_source']['categories']}" for hit in results]
    )
    prompt = f"""
    User Query: {query}
    Context: {context}
    Response:"""

    response = openai.ChatCompletion.create(
        model="gpt-3.5-turbo",
        messages=[
            {"role": "system", "content": "You are an AI assistant helping users find relevant restaurant recommendations."},
            {"role": "user", "content": prompt}
        ],
        max_tokens=150,
        temperature=0.7
    )
    return response.choices[0].message['content'].strip()


@app.route("/api/v1/assistant/query", methods=["POST"])
def assistant_query():
    try:
        data = request.get_json()
        query = data.get("query")

        if not query:
            return jsonify({"error": "Query is required"}), 400

        embedding_response = openai.Embedding.create(
            input=[query],
            model="text-embedding-ada-002"
        )
        query_embedding = embedding_response["data"][0]["embedding"]

        results = check_relevance(query_embedding)

        if results:
            response_text = generate_response(results, query)
            return jsonify({"response": response_text}), 200
        else:
            return jsonify({"response": "I'm sorry, I couldn't find relevant information for your query."}), 200

    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0", port=5000)