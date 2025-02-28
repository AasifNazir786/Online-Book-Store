import PropTypes from "prop-types";
import "./bookCard.css";

const BookCard = ({ book, onBuyNow, onAddToCart }) => {
    console.log('the book is: ' + (book.author ? book.author : 'Unknown Author'));
    return (
        <div className="book-card">
            <img src={book.image} alt={book.title} className="book-image" />
            <div className="book-details">
                <h3 className="book-title">{book.title}</h3>
                <p className="book-author"><strong>By </strong>{book.author}</p>
                <p className="book-genre"><strong>Genre: </strong>{book.genre}</p>
                <p className="book-price"><strong>Price: </strong>${book.price.toFixed(2)}</p>
                <div className="book-actions">
                    <button className="buy-now" onClick={() => onBuyNow(book)}>
                        Buy Now
                    </button>
                    <button className="add-to-cart" onClick={() => onAddToCart(book)}>
                        Add to Cart
                    </button>
                </div>
            </div>
        </div>
    );
};

BookCard.propTypes = {
    book: PropTypes.object.isRequired,
    onBuyNow: PropTypes.func.isRequired,
    onAddToCart: PropTypes.func.isRequired
};

export default BookCard;
