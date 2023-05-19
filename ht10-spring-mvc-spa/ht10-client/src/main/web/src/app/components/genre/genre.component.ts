import {Component, OnInit, ViewChild} from '@angular/core';
import {SharedService} from "../../services/shared.service";
import {GenreService} from "../../services/genre.service";
import {GenreDto} from "../../models/genre-dto";
import {MatTable} from "@angular/material/table";
import {MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition} from "@angular/material/snack-bar";
import {BookService} from "../../services/book.service";


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

    private horizontalPosition: MatSnackBarHorizontalPosition = 'end';
    private verticalPosition: MatSnackBarVerticalPosition = 'top';

    constructor(public shared: SharedService,
                private genreService: GenreService,
                private bookService: BookService,
                private snackBar: MatSnackBar) {
    }

    ngOnInit(): void {
        this.loadGenres();
    }

    private openSnackBar(snackBarText: string): void {
        this.snackBar.open(snackBarText, 'End now', {
            duration: 2000,
            horizontalPosition: this.horizontalPosition,
            verticalPosition: this.verticalPosition,
        });
    }

    private loadGenres(): void {
        this.genreService.getAll().subscribe(data => {
            this.shared.genres = data.genres;
        })
    }

    public makeGenreEdit(element: GenreDto): void {
        this.cancelEditingOtherElements();
        element.isEdit = true;
        this.updatedGenre = new GenreDto(element.id, element.name);
    }

    public cancelGenreEdit(element: GenreDto): void {
        element.isEdit = false;
        if (!element.id) {
            this.shared.genres.splice(this.shared.genres.findIndex(g => g === element), 1);
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
        })
    }

    private updateBooks(): void {
        this.bookService.getAll().subscribe(data => {
            this.shared.books = data.books;
        })
    }

    private updateGenres(element: GenreDto, genre: GenreDto): void {
        let index = this.getElementIndexInGenresArray(element);

        this.shared.genres.splice(index, 1, genre);
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

        this.shared.genres.splice(index, 1);
        this.genreTable.renderRows();
    }

    private getElementIndexInGenresArray(element: GenreDto): number {
        return this.shared.genres.map(function (item) {
            return item.id
        }).indexOf(element.id)
    }

    public newGenre(): void {
        this.cancelEditingOtherElements();
        let genre = new GenreDto(null, null);
        genre.isEdit = true;
        this.shared.genres.push(genre);
        this.genreTable.renderRows();
    }

    public isGenreReadyToUpdate(element: GenreDto): boolean {
        return !this.shared.isBlank(element.name);
    }

    private cancelEditingOtherElements(): void {
        function isEditing(element, index, array) {
            return (element.isEdit);
        }

        let filtered = this.shared.genres.filter(isEditing);
        if (filtered.length > 0) {
            this.cancelGenreEdit(filtered[0]);
        }
    }

}
