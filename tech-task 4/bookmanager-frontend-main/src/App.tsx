import React, {useEffect} from 'react';
import {useDispatch} from 'react-redux';
import {setBooks} from './features/bookReducer';
import BooksList from './components/BooksList';
import AddBook from './components/AddBook';
import DeleteBook from './components/DeleteBook';
import Sidebar from './components/Sidebar';
import Footer from './components/Footer';
import {fetchBooks} from './api/api';

const App: React.FC = () => {
    const dispatch = useDispatch();

    useEffect(() => {
        const loadBooks = async () => {
            const books = await fetchBooks();
            dispatch(setBooks(books));
        };

        loadBooks();
    }, [dispatch]);

    return (
        <div>
            <h1>Book Management</h1>
            <p>Welcome to the Book Management System</p>
        
            <AddBook/>
            <BooksList/>
            <DeleteBook/>
            <Footer/>
        </div>
    );
};

export default App;

