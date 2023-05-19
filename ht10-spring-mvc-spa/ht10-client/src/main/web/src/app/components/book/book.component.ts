import {Component, OnInit, ViewChild} from '@angular/core';
import {SharedService} from "../../services/shared.service";
import {BookService} from "../../services/book.service";
import {BookDto} from "../../models/book-dto";
import {AuthorService} from "../../services/author.service";
import {GenreService} from "../../services/genre.service";
import {MatTable} from "@angular/material/table";
import {Router} from "@angular/router";

@Component({
    selector: 'book',
    templateUrl: './book.component.html',
    styleUrls: [
        './book.component.css'
    ]
})

export class BookComponent implements OnInit {

    public bookDisplayedColumns: string[] = ['id', 'title', 'author', 'genre', 'actions'];

    private updatedBook: BookDto = new BookDto(null, null, null, null, null);

    @ViewChild('bookTable') bookTable: MatTable<any>;

    constructor(public shared: SharedService,
                private bookService: BookService,
                private authorService: AuthorService,
                private genreService: GenreService,
                private router: Router) {
    }

    ngOnInit(): void {
        this.loadBooks();
        this.loadGenres();
        this.loadAuthors();
    }

    private loadBooks(): void {
        this.bookService.getAll().subscribe(data => {
            this.shared.books = data.books;
        })
    }

    private loadGenres(): void {
        this.genreService.getAll().subscribe(data => {
            this.shared.genres = data.genres;
        })
    }

    private loadAuthors(): void {
        this.authorService.getAll().subscribe(data => {
            this.shared.authors = data.authors;
        })
    }

    public makeBookEdit(element: BookDto): void {
        this.cancelEditingOtherElements();
        element.isEdit = true;
        this.updatedBook = new BookDto(element.id, element.title, element.author, element.genre, []);
    }

    public cancelBookEdit(book: BookDto): void {
        book.isEdit = false;
        if (!book.id) {
            this.shared.books.splice(this.shared.books.findIndex(b => b === book), 1);
            this.bookTable.renderRows();
        } else {
            book.title = this.updatedBook.title;
            book.genre = this.updatedBook.genre;
            book.author = this.updatedBook.author;
        }
    }

    public updateBook(book: BookDto): void {
        this.bookService.save(book).subscribe(data => {
            this.updateBooks(book, data);
            book.isEdit = false;
            this.bookTable.renderRows();
        })
    }

    private updateBooks(element: BookDto, book: BookDto): void {
        let index = this.getElementIndexInBooksArray(element);

        this.shared.books.splice(index, 1, book);
    }

    public removeBook(element: BookDto): void {
        this.bookService.removeById(element.id).subscribe(data => {
            this.removeBookFromArrayById(element);
        })
    }

    private removeBookFromArrayById(element: BookDto): void {
        let index = this.getElementIndexInBooksArray(element);

        this.shared.books.splice(index, 1);
        this.bookTable.renderRows();
    }

    private getElementIndexInBooksArray(element: BookDto): number {
        return this.shared.books.map(function (item) {
            return item.id
        }).indexOf(element.id)
    }

    public newBook(): void {
        this.cancelEditingOtherElements();
        let book = new BookDto(null, null, null, null, []);
        book.isEdit = true;
        this.shared.books.push(book);
        this.bookTable.renderRows();
    }

    public isBookReadyToUpdate(element: BookDto): boolean {
        return !this.shared.isBlank(element.title);
    }

    public objectComparisonFunction = function (option: any, value: any): boolean {
        return option.id === value.id;
    }

    public openComments(id: string): void {
        this.router.navigate(['book/' + id + '/comments'])
    }

    private cancelEditingOtherElements(): void {
        function isEditing(element, index, array) {
            return (element.isEdit);
        }

        let filtered = this.shared.books.filter(isEditing);
        if (filtered.length > 0) {
            this.cancelBookEdit(filtered[0]);
        }
    }

}
