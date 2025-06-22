import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { addBook } from '../features/bookReducer';
import { createBook } from '../api/api';
import Form from "react-bootstrap/Form";

const AddBook = () => {
    const dispatch = useDispatch();
    const [title, setTitle] = useState('');
    const [authors, setAuthors] = useState('');
    const [publishedDate, setPublishedDate] = useState('');

    const handleAddBook = async () => {
    const authorsArray = authors
        .split(',')
        .map(a => a.trim())
        .filter(a => a.length > 0);

    if (!title || authorsArray.length === 0 || !publishedDate) return;
    const newBook = await createBook({ title, authors: authorsArray, publishedDate });
    console.log('createBook:', createBook);
    dispatch(addBook(newBook));
    setTitle('');
    setAuthors('');
    setPublishedDate('');
};

    return (
        <div>
            <h2>Add new Book</h2>
            <input
                type="text"
                placeholder="Title"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
            />
            <input
                type="text"
                placeholder="Authors (comma separated)"
                value={authors}
                onChange={(e) => setAuthors(e.target.value)}
            />
            <Form.Control
                type="date"
                value={publishedDate}
                onChange={(e) => setPublishedDate(e.target.value)}
            />
            <button onClick={handleAddBook}>Add</button>
        </div>
    );
};

export default AddBook;