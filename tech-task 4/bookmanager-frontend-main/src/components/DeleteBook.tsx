import React, {useState} from 'react';
import {useDispatch} from 'react-redux';
import {deleteFeature} from '../features/bookReducer';
import {deleteBook} from '../api/api';

const DeleteBook = () => {
    const dispatch = useDispatch();
    const [bookId, getBookId] = useState('');

    const handleDeleteBook = async () => {
        await deleteBook(1);
        dispatch(deleteFeature(1));
        getBookId('');
    };

    return (
        <div>
            <h2>Delete Book</h2>
            <input
                type="text"
                placeholder="Book ID"
                value={bookId}
                onChange={(e) => getBookId(e.target.value)}
            />
            <button onClick={handleDeleteBook}>Delete</button>
        </div>
    );
};

export default DeleteBook;