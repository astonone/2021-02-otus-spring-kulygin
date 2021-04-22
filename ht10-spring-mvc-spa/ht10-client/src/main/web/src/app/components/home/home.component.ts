import {Component, OnInit, ViewChild} from '@angular/core';
import {SharedService} from "../../services/shared.service";
import {BookService} from "../../services/book.service";
import {BookDto} from "../../models/book-dto";
import {AuthorService} from "../../services/author.service";
import {GenreService} from "../../services/genre.service";
import {GenreDto} from "../../models/genre-dto";
import {AuthorDto} from "../../models/author-dto";
import {MatTable} from "@angular/material/table";
import {MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition} from "@angular/material/snack-bar";
import {Router} from "@angular/router";


@Component({
    selector: 'home',
    templateUrl: './home.component.html',
    styleUrls: [
        './home.component.css'
    ]
})

export class HomeComponent implements OnInit {

    public books: BookDto[] = [];
    public genres: GenreDto[] = [];
    public authors: AuthorDto[] = [];
    public bookDisplayedColumns: string[] = ['id', 'title', 'author', 'genre', 'actions'];
    public genreDisplayedColumns: string[] = ['id', 'name', 'actions'];
    public authorDisplayedColumns: string[] = ['id', 'firstName', 'lastName', 'actions'];

    private updatedGenre: GenreDto = new GenreDto(null, null);
    private updatedAuthor: AuthorDto = new AuthorDto(null, null, null);
    private updatedBook: BookDto = new BookDto(null, null, null, null, null);

    @ViewChild('genreTable') genreTable: MatTable<any>;
    @ViewChild('authorTable') authorTable: MatTable<any>;
    @ViewChild('bookTable') bookTable: MatTable<any>;

    private horizontalPosition: MatSnackBarHorizontalPosition = 'end';
    private verticalPosition: MatSnackBarVerticalPosition = 'top';

    constructor(private shared: SharedService,
                private bookService: BookService,
                private authorService: AuthorService,
                private genreService: GenreService,
                private snackBar: MatSnackBar,
                private router: Router) {
    }

    ngOnInit(): void {
        this.loadBooks();
        this.loadGenres();
        this.loadAuthors();
    }

    private openSnackBar(snackBarText: string): void {
        this.snackBar.open(snackBarText, 'End now', {
            duration: 2000,
            horizontalPosition: this.horizontalPosition,
            verticalPosition: this.verticalPosition,
        });
    }

    private loadBooks(): void {
        this.bookService.getAll().subscribe(data => {
            this.books = data.books;
        })
    }

    private loadGenres(): void {
        this.genreService.getAll().subscribe(data => {
            this.genres = data.genres;
        })
    }

    private loadAuthors(): void {
        this.authorService.getAll().subscribe(data => {
            this.authors = data.authors;
        })
    }

    public makeGenreEdit(element: GenreDto): void {
        element.isEdit = true;
        this.updatedGenre = new GenreDto(element.id, element.name);
    }

    public cancelGenreEdit(element: GenreDto): void {
        element.isEdit = false;
        if (!element.id) {
            this.genres.splice(this.genres.findIndex(g => g === element), 1);
            this.genreTable.renderRows();
        } else {
            element.name = this.updatedGenre.name;
        }
    }

    public updateGenre(element: GenreDto): void {
        this.genreService.save(element).subscribe(data => {
            this.updateGenres(element, data);
            element.isEdit = false;
            this.genreTable.renderRows();
            this.loadBooks();
        })
    }

    private updateGenres(element: GenreDto, genre: GenreDto): void {
        let index = this.getElementIndexInGenresArray(element);

        this.genres.splice(index, 1, genre);
    }

    public removeGenre(element: GenreDto): void {
        this.genreService.removeById(element.id).subscribe(data => {
            this.removeGenreFromArrayById(element);
        }, error => {
            this.openSnackBar(error.error.message);
        })
    }

    private removeGenreFromArrayById(element: GenreDto): void {
        let index = this.getElementIndexInGenresArray(element);

        this.genres.splice(index, 1);
        this.genreTable.renderRows();
    }

    private getElementIndexInGenresArray(element: GenreDto): number {
        return this.genres.map(function (item) {
            return item.id
        }).indexOf(element.id)
    }

    public newGenre(): void {
        let genre = new GenreDto(null, null);
        genre.isEdit = true;
        this.genres.push(genre);
        this.genreTable.renderRows();
    }

    public isGenreReadyToUpdate(element: GenreDto): boolean {
        return !this.shared.isBlank(element.name);
    }

    public makeAuthorEdit(element: AuthorDto): void {
        element.isEdit = true;
        this.updatedAuthor = new AuthorDto(element.id, element.firstName, element.lastName);
    }

    public cancelAuthorEdit(element: AuthorDto): void {
        element.isEdit = false;
        if (!element.id) {
            this.authors.splice(this.authors.findIndex(a => a === element), 1);
            this.authorTable.renderRows();
        } else {
            element.firstName = this.updatedAuthor.firstName;
            element.lastName = this.updatedAuthor.lastName;
        }
    }

    public updateAuthor(element: AuthorDto): void {
        this.authorService.save(element).subscribe(data => {
            this.updateAuthors(element, data);
            element.isEdit = false;
            this.authorTable.renderRows();
            this.loadBooks();
        })
    }

    private updateAuthors(element: AuthorDto, author: AuthorDto): void {
        let index = this.getElementIndexInAuthorsArray(element);

        this.authors.splice(index, 1, author);
    }

    public removeAuthor(element: AuthorDto): void {
        this.authorService.removeById(element.id).subscribe(data => {
            this.removeAuthorFromArrayById(element);
        }, error => {
            this.openSnackBar(error.error.message);
        })
    }

    private removeAuthorFromArrayById(element: AuthorDto): void {
        let index = this.getElementIndexInAuthorsArray(element);

        this.authors.splice(index, 1);
        this.authorTable.renderRows();
    }

    private getElementIndexInAuthorsArray(element: AuthorDto): number {
        return this.authors.map(function (item) {
            return item.id
        }).indexOf(element.id)
    }

    public newAuthor(): void {
        let author = new AuthorDto(null, null, null);
        author.isEdit = true;
        this.authors.push(author);
        this.authorTable.renderRows();
    }

    public isAuthorReadyToUpdate(element: AuthorDto): boolean {
        return !this.shared.isBlank(element.firstName) && !this.shared.isBlank(element.lastName);
    }

    public makeBookEdit(element: BookDto): void {
        element.isEdit = true;
        this.updatedBook = new BookDto(element.id, element.title, element.author, element.genre, []);
    }

    public cancelBookEdit(book: BookDto): void {
        book.isEdit = false;
        if (!book.id) {
            this.books.splice(this.books.findIndex(b => b === book), 1);
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

        this.books.splice(index, 1, book);
    }

    public removeBook(element: BookDto): void {
        this.bookService.removeById(element.id).subscribe(data => {
            this.removeBookFromArrayById(element);
        })
    }

    private removeBookFromArrayById(element: BookDto): void {
        let index = this.getElementIndexInBooksArray(element);

        this.books.splice(index, 1);
        this.bookTable.renderRows();
    }

    private getElementIndexInBooksArray(element: BookDto): number {
        return this.books.map(function (item) {
            return item.id
        }).indexOf(element.id)
    }

    public newBook(): void {
        let book = new BookDto(null, null, null, null, []);
        book.isEdit = true;
        this.books.push(book);
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

}
