from app import db
import uuid  # Import uuid to generate UUIDs

class Restaurant(db.Model):
    __tablename__ = 'restaurants'

    # business_id is now the primary key
    business_id = db.Column(db.String(36), primary_key=True, default=str(uuid.uuid4()))  # UUID as primary key
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

class RestaurantImage(db.Model):
    __tablename__ = 'restaurant_images'

    photo_id = db.Column(db.String(255), primary_key=True)
    business_id = db.Column(db.String(36), db.ForeignKey('restaurants.business_id'), nullable=False)
    caption = db.Column(db.Text)
    label = db.Column(db.String(100))

    # Define a relationship with the Restaurant model (optional)
    restaurant = db.relationship('Restaurant', backref='images', lazy=True)

    def __repr__(self):
        return f'<RestaurantImage {self.photo_id}>'
