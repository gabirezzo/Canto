import React from 'react';
import { Link } from 'react-router-dom';
import './Sidebar.css'; // Optional: for styling

const Sidebar = () => { 
 return (
  <nav className="sidebar">
    <h2>Book Manager</h2>
    <ul>
      <li>
        <Link to="/">Home</Link>
      </li>
      <li>
        <Link to="/books">Books</Link>
      </li>
      <li>
        <Link to="/add-book">Add Book</Link>
      </li>
      <li>
        <Link to="/delete-book">Delete Book</Link>
      </li>
      {/* Add more links as needed */}
    </ul>
  </nav>
)
};

export default Sidebar;


