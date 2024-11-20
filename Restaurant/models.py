from app import db
import uuid  # Import uuid to generate UUIDs

class Restaurant(db.Model):
    __tablename__ = 'restaurants'

    # Change restaurant_id to be of type UUID
    id = db.Column(db.String(36), primary_key=True, default=str(uuid.uuid4()))  # UUID as primary key
    name = db.Column(db.String(255))
    address = db.Column(db.Text)
    city = db.Column(db.String(100))
    state = db.Column(db.String(100))
    postal_code = db.Column(db.String(20))
    latitude = db.Column(db.Float)
    longitude = db.Column(db.Float)
    average_rating = db.Column(db.Float)
    review_count = db.Column(db.Integer)
    is_open = db.Column(db.Boolean)
    categories = db.Column(db.ARRAY(db.String))
    menu = db.Column(db.ARRAY(db.String))
    hours = db.Column(db.JSON)

    def __repr__(self):
        return f'<Restaurant {self.name}>'
