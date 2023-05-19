import {Component, OnInit, ViewChild} from '@angular/core';
import {LocalStorageService} from "../../services/local-storage.service";
import {BookService} from "../../services/book.service";
import {BookDto} from "../../models/book-dto";
import {AuthorService} from "../../services/author.service";
import {GenreService} from "../../services/genre.service";
import {MatTable} from "@angular/material/table";
import {Router} from "@angular/router";
import {UserService} from "../../services/user.service";
import {SharedService} from "../../services/shared.service";

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

    constructor(public localStorageService: LocalStorageService,
                private bookService: BookService,
                private authorService: AuthorService,
                private genreService: GenreService,
                private router: Router,
                private userService: UserService,
                private shared: SharedService) {
    }

    ngOnInit(): void {
        this.loadBooks();
        this.loadGenres();
        this.loadAuthors();
    }

    private loadBooks(): void {
        this.bookService.getAll().subscribe(data => {
            if (data.books.length === 1 && data.books[0].id === 'N/A') {
                this.shared.openSnackBar("Seems now our services unavailable. Please try again later");
            } else {
                this.localStorageService.books = data.books;
            }
        }, error => {
            if (error.status === 403) {
                this.shared.openSnackBar("You don't have rights for this action!");
            } else {
                this.shared.openSnackBar(error.error.message);
            }
        });
    }

    private loadGenres(): void {
        this.genreService.getAll().subscribe(data => {
            if (data.genres.length === 1 && data.genres[0].id === 'N/A') {
                this.shared.openSnackBar("Seems now our services unavailable. Please try again later");
            } else {
                this.localStorageService.genres = data.genres;
            }
        }, error => {
            if (error.status === 403) {
                this.shared.openSnackBar("You don't have rights for this action!");
            } else {
                this.shared.openSnackBar(error.error.message);
            }
        });
    }

    private loadAuthors(): void {
        this.authorService.getAll().subscribe(data => {
            if (data.authors.length === 1 && data.authors[0].id === 'N/A') {
                this.shared.openSnackBar("Seems now our services unavailable. Please try again later");
            } else {
                this.localStorageService.authors = data.authors;
            }
        }, error => {
            if (error.status === 403) {
                this.shared.openSnackBar("You don't have rights for this action!");
            } else {
                this.shared.openSnackBar(error.error.message);
            }
        });
    }

    public makeBookEdit(element: BookDto): void {
        this.cancelEditingOtherElements();
        element.isEdit = true;
        this.updatedBook = new BookDto(element.id, element.title, element.author, element.genre, []);
    }

    public cancelBookEdit(book: BookDto): void {
        book.isEdit = false;
        if (!book.id) {
            this.localStorageService.books.splice(this.localStorageService.books.findIndex(b => b === book), 1);
            this.bookTable.renderRows();
        } else {
            book.title = this.updatedBook.title;
            book.genre = this.updatedBook.genre;
            book.author = this.updatedBook.author;
        }
    }

    public updateBook(book: BookDto): void {
        this.bookService.save(book).subscribe(data => {
            if (data.id === 'N/A') {
                this.shared.openSnackBar("Seems now our services unavailable. Please try again later");
            } else {
                this.updateBooks(book, data);
                book.isEdit = false;
                this.bookTable.renderRows();
            }
        }, error => {
            if (error.status === 403) {
                this.shared.openSnackBar("You don't have rights for this action!");
            } else {
                this.shared.openSnackBar(error.error.message);
            }
        });
    }

    private updateBooks(element: BookDto, book: BookDto): void {
        let index = this.getElementIndexInBooksArray(element);

        this.localStorageService.books.splice(index, 1, book);
    }

    public removeBook(element: BookDto): void {
        this.bookService.removeById(element.id).subscribe(data => {
            this.removeBookFromArrayById(element);
        }, error => {
            if (error.status === 403) {
                this.shared.openSnackBar("You don't have rights for this action!");
            } else {
                this.shared.openSnackBar(error.error.message);
            }
        });
    }

    private removeBookFromArrayById(element: BookDto): void {
        let index = this.getElementIndexInBooksArray(element);

        this.localStorageService.books.splice(index, 1);
        this.bookTable.renderRows();
    }

    private getElementIndexInBooksArray(element: BookDto): number {
        return this.localStorageService.books.map(function (item) {
            return item.id
        }).indexOf(element.id);
    }

    public newBook(): void {
        this.cancelEditingOtherElements();
        let book = new BookDto(null, null, null, null, []);
        book.isEdit = true;
        this.localStorageService.books.push(book);
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

        let filtered = this.localStorageService.books.filter(isEditing);
        if (filtered.length > 0) {
            this.cancelBookEdit(filtered[0]);
        }
    }

}
