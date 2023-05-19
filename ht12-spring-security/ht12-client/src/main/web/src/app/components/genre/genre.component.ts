import {Component, OnInit, ViewChild} from '@angular/core';
import {LocalStorageService} from "../../services/local-storage.service";
import {GenreService} from "../../services/genre.service";
import {GenreDto} from "../../models/genre-dto";
import {MatTable} from "@angular/material/table";
import {BookService} from "../../services/book.service";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import {SharedService} from "../../services/shared.service";


@Component({
    selector: 'genre',
    templateUrl: './genre.component.html',
    styleUrls: [
        './genre.component.css'
    ]
})

export class GenreComponent implements OnInit {

    public genreDisplayedColumns: string[] = ['id', 'name', 'actions'];

    private updatedGenre: GenreDto = new GenreDto(null, null);

    @ViewChild('genreTable') genreTable: MatTable<any>;

    constructor(public localStorageService: LocalStorageService,
                private genreService: GenreService,
                private bookService: BookService,
                private userService: UserService,
                private router: Router,
                private shared: SharedService) {
    }

    ngOnInit(): void {
        this.loadGenres();
    }

    private loadGenres(): void {
        this.genreService.getAll().subscribe(data => {
            this.localStorageService.genres = data.genres;
        }, error => {
            this.shared.openSnackBar(error.error.message);
        });
    }

    public makeGenreEdit(element: GenreDto): void {
        this.cancelEditingOtherElements();
        element.isEdit = true;
        this.updatedGenre = new GenreDto(element.id, element.name);
    }

    public cancelGenreEdit(element: GenreDto): void {
        element.isEdit = false;
        if (!element.id) {
            this.localStorageService.genres.splice(this.localStorageService.genres.findIndex(g => g === element), 1);
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

    private updateGenres(element: GenreDto, genre: GenreDto): void {
        let index = this.getElementIndexInGenresArray(element);

        this.localStorageService.genres.splice(index, 1, genre);
    }

    public removeGenre(element: GenreDto): void {
        this.genreService.removeById(element.id).subscribe(data => {
            this.removeGenreFromArrayById(element);
        }, error => {
            this.shared.openSnackBar(error.error.message);
        })
    }

    private removeGenreFromArrayById(element: GenreDto): void {
        let index = this.getElementIndexInGenresArray(element);

        this.localStorageService.genres.splice(index, 1);
        this.genreTable.renderRows();
    }

    private getElementIndexInGenresArray(element: GenreDto): number {
        return this.localStorageService.genres.map(function (item) {
            return item.id
        }).indexOf(element.id);
    }

    public newGenre(): void {
        this.cancelEditingOtherElements();
        let genre = new GenreDto(null, null);
        genre.isEdit = true;
        this.localStorageService.genres.push(genre);
        this.genreTable.renderRows();
    }

    public isGenreReadyToUpdate(element: GenreDto): boolean {
        return !this.shared.isBlank(element.name);
    }

    private cancelEditingOtherElements(): void {
        function isEditing(element, index, array) {
            return (element.isEdit);
        }

        let filtered = this.localStorageService.genres.filter(isEditing);
        if (filtered.length > 0) {
            this.cancelGenreEdit(filtered[0]);
        }
    }

}
