from prometheus_client import Counter, Histogram

REQUEST_COUNT = Counter(
    'request_count',
    'Request count by method and endpoint',
    ['method', 'endpoint']
)

REQUEST_LATENCY = Histogram(
    'request_latency_seconds',
    'Request latency in seconds',
    ['endpoint']
)
