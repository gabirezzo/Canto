import {Book} from '../types/Book';

const GRAPHQL_URL = 'http://localhost:8080/graphql';

export const fetchBooks = async (): Promise<Book[]> => {
    const response = await fetch(GRAPHQL_URL, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            query: `{
                findAllBooks {
                    id
                    title
                    authors {
                        name
                    }
                    publishedDate
                }
            }`,
        }),
    });

    const json = await response.json();
    console.log('GraphQL response:', json); // <-- Add this line
    if (!json.data) throw new Error('No data returned from GraphQL');
    return json.data.findAllBooks;
};

export const fetchBookById = async (id: number): Promise<Book> => {
    const response = await fetch(GRAPHQL_URL, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            query: `{
        findBookById($id: Long!){
          id
          title
          authors 
          publishedDate
        }
      }`,
            variables: {id: id},
        }),
    });

    const {data} = await response.json();
    return data.findBookById;
};

export const createBook = async (book: Omit<Book, 'id'>): Promise<Book> => {
    const response = await fetch(GRAPHQL_URL, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            query: `mutation($title: String!, $authors: [String!]!, $publishedDate: String!) {
        createBook(title: $title, authors: $authors, publishedDate: $publishedDate) {
          id
          title
          authors {
           name
          }
          publishedDate
        }
      }`,
            variables: book,
        }),
    });

    const {data} = await response.json();
    return data.createBook;
};

export const deleteBook = async (id: number): Promise<number> => {
    const response = await fetch(GRAPHQL_URL, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            query: `mutation($id: Long!) {
        deleteBook(id: $id) {
          id
        }
      }`,
            variables: {id: id},
        }),
    });

    const {data} = await response.json();
    return data.deleteBook;
};
