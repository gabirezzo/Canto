import React, { useEffect, useState } from 'react';
import { fetchBooks } from '../api/api';

const BooksList = () => {
    const [books, setBooks] = useState<any[]>([]);

    useEffect(() => {
        fetchBooks().then(setBooks);
    }, []);

    return (
        <div>
            <h2>Books</h2>
            <ul>
                {books.map(book => (
                    <li key={book.id}>
                        {book.title} by {book.authors.map((a: any) => a.name).join(', ')} (Published: {book.publishedDate})
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default BooksList;