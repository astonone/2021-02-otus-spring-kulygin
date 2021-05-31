import {Component, OnInit, ViewChild} from '@angular/core';
import {LocalStorageService} from "../../services/local-storage.service";
import {AuthorService} from "../../services/author.service";
import {AuthorDto} from "../../models/author-dto";
import {MatTable} from "@angular/material/table";
import {BookService} from "../../services/book.service";
import {SharedService} from "../../services/shared.service";


@Component({
    selector: 'author',
    templateUrl: './author.component.html',
    styleUrls: [
        './author.component.css'
    ]
})

export class AuthorComponent implements OnInit {

    public authorDisplayedColumns: string[] = ['id', 'firstName', 'lastName', 'actions'];

    private updatedAuthor: AuthorDto = new AuthorDto(null, null, null);

    @ViewChild('authorTable') authorTable: MatTable<any>;

    constructor(public localStorageService: LocalStorageService,
                private shared: SharedService,
                private authorService: AuthorService,
                private bookService: BookService) {
    }

    ngOnInit(): void {
        this.loadAuthors();
    }

    private loadAuthors(): void {
        this.authorService.getAll().subscribe(data => {
            this.localStorageService.authors = data.authors;
        }, error => {
            this.shared.openSnackBar(error.error.message);
        });
    }

    public makeAuthorEdit(element: AuthorDto): void {
        this.cancelEditingOtherElements();
        element.isEdit = true;
        this.updatedAuthor = new AuthorDto(element.id, element.firstName, element.lastName);
    }

    public cancelAuthorEdit(element: AuthorDto): void {
        element.isEdit = false;
        if (!element.id) {
            this.localStorageService.authors.splice(this.localStorageService.authors.findIndex(a => a === element), 1);
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
            this.updateBooks();
        }, error => {
            this.shared.openSnackBar(error.error.message);
        });
    }

    private updateBooks(): void {
        this.bookService.getAll().subscribe(data => {
            this.localStorageService.books = data.books;
        }, error => {
            this.shared.openSnackBar(error.error.message);
        });
    }

    private updateAuthors(element: AuthorDto, author: AuthorDto): void {
        let index = this.getElementIndexInAuthorsArray(element);

        this.localStorageService.authors.splice(index, 1, author);
    }

    public removeAuthor(element: AuthorDto): void {
        this.authorService.removeById(element.id).subscribe(data => {
            this.removeAuthorFromArrayById(element);
        }, error => {
            this.shared.openSnackBar(error.error.message);
        });
    }

    private removeAuthorFromArrayById(element: AuthorDto): void {
        let index = this.getElementIndexInAuthorsArray(element);

        this.localStorageService.authors.splice(index, 1);
        this.authorTable.renderRows();
    }

    private getElementIndexInAuthorsArray(element: AuthorDto): number {
        return this.localStorageService.authors.map(function (item) {
            return item.id
        }).indexOf(element.id);
    }

    public newAuthor(): void {
        this.cancelEditingOtherElements();
        let author = new AuthorDto(null, null, null);
        author.isEdit = true;
        this.localStorageService.authors.push(author);
        this.authorTable.renderRows();
    }

    public isAuthorReadyToUpdate(element: AuthorDto): boolean {
        return !this.shared.isBlank(element.firstName) && !this.shared.isBlank(element.lastName);
    }

    private cancelEditingOtherElements(): void {
        function isEditing(element, index, array) {
            return (element.isEdit);
        }

        let filtered = this.localStorageService.authors.filter(isEditing);
        if (filtered.length > 0) {
            this.cancelAuthorEdit(filtered[0]);
        }
    }

}
