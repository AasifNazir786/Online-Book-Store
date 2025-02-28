import { useEffect, useState } from "react";
import { getBooks } from "../../services/Api.jsx";
import BookCard from "../BookCard/Card.jsx";
import "./body.css";

const Body = () => {
    const [books, setBooks] = useState([]);
    const [searchQuery, setSearchQuery] = useState("");
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [page, setPage] = useState(0); // Current page
    const [size, setSize] = useState(10); // Number of items per page
    const [totalPages, setTotalPages] = useState(0); // Total number of pages

    useEffect(() => {
        const fetchBooks = async () => {
            try {
                const data = await getBooks(page, size);
                if (data && Array.isArray(data.content)) {
                    setBooks(data.content);
                    setTotalPages(data.totalPages);
                } else {
                    setBooks([]);
                    setTotalPages(0);
                }
            } catch (error) {
                setError("Failed to fetch books. Please try again later. "+ error);
            } finally {
                setLoading(false);
            }
        };
        fetchBooks();
    }, [page, size]); // Re-fetch books when page or size changes

    const filteredBooks = books.filter((book) =>
        book.bookTitle.toLowerCase().includes(searchQuery.toLowerCase())
    );

    const handleBuyNow = (book) => {
        console.log("Buying book:", book.title);
    };

    const handleAddToCart = (book) => {
        console.log("Adding book to cart:", book.title);
    };

    const handlePageChange = (newPage) => {
        if (newPage >= 0 && newPage < totalPages) {
            setPage(newPage);
        }
    };

    const handleSizeChange = (newSize) => {
        setSize(newSize);
        setPage(0); // Reset to the first page when size changes
    };

    if (loading) {
        return <div className="loading-spinner">Loading...</div>;
    }

    if (error) {
        return <div className="error-message">{error}</div>;
    }

    return (
        <div className="body-container">
            <div className="search-bar">
                <input
                    type="text"
                    placeholder="Search books..."
                    value={searchQuery}
                    onChange={(e) => setSearchQuery(e.target.value)}
                />
            </div>

            <div className="pagination-controls">
                <label>
                    Items per page:
                    <select
                        value={size}
                        onChange={(e) => handleSizeChange(Number(e.target.value))}
                    >
                        <option value={5}>5</option>
                        <option value={10}>10</option>
                        <option value={20}>20</option>
                        <option value={30}>30</option>
                    </select>
                </label>

                <button
                    onClick={() => handlePageChange(page - 1)}
                    disabled={page === 0}
                >
                    Previous
                </button>
                <span>
                    Page {page + 1} of {totalPages}
                </span>
                <button
                    onClick={() => handlePageChange(page + 1)}
                    disabled={page >= totalPages - 1}
                >
                    Next
                </button>
            </div>

            {filteredBooks.length === 0 ? (
                <div className="no-books-message">No books found.</div>
            ) : (
                <>
                    <section className="book-section">
                        <h2>Recent Books</h2>
                        <div className="book-list">
                            {filteredBooks
                                .filter((book) => book.isRecent)
                                .map((book) => (
                                    <BookCard
                                        key={book.id}
                                        book={{
                                            image: book.imageUrl,
                                            title: book.bookTitle,
                                            author: book.authorName,
                                            price: book.bookPrice,
                                            genre: book.genre,
                                        }}
                                        onBuyNow={handleBuyNow}
                                        onAddToCart={handleAddToCart}
                                    />
                                ))}
                        </div>
                    </section>

                    <section className="book-section">
                        <h2>Awarded Books</h2>
                        <div className="book-list">
                            {filteredBooks
                                .filter((book) => book.isAwarded)
                                .map((book) => (
                                    <BookCard
                                        key={book.id}
                                        book={{
                                            image: book.imageUrl,
                                            title: book.bookTitle,
                                            author: book.authorName,
                                            price: book.bookPrice,
                                            genre: book.genre,
                                        }}
                                        onBuyNow={handleBuyNow}
                                        onAddToCart={handleAddToCart}
                                    />
                                ))}
                        </div>
                    </section>

                    <section className="book-section">
                        <h2>All Books</h2>
                        <div className="book-list">
                            {filteredBooks.map((book) => (
                                <BookCard
                                    key={book.id}
                                    book={{
                                        image: book.imageUrl,
                                        title: book.bookTitle,
                                        author: book.authorName,
                                        price: book.bookPrice,
                                        genre: book.genre,
                                    }}
                                    onBuyNow={handleBuyNow}
                                    onAddToCart={handleAddToCart}
                                />
                            ))}
                        </div>
                    </section>
                </>
            )}
        </div>
    );
};

export default Body;