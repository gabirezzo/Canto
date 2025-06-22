import React, {useState} from 'react';
import {useDispatch} from 'react-redux';
import {deleteFeature} from '../features/bookReducer';
import {deleteBook} from '../api/api';

const DeleteBook = () => {
    const dispatch = useDispatch();
    const [bookId, setBookId] = useState('');

    const handleDeleteBook = async () => {
        if (!bookId) return;
        const id = Number(bookId);
        if (isNaN(id)) return;
        await deleteBook(id);
        dispatch(deleteFeature(id));
        setBookId('');
    };

    return (
        <div>
            <h2>Delete Book</h2>
            <input
                type="text"
                placeholder="Book ID"
                value={bookId}
                onChange={(e) => setBookId(e.target.value)}
            />
            <button onClick={handleDeleteBook}>Delete</button>
        </div>
    );
};

export default DeleteBook;