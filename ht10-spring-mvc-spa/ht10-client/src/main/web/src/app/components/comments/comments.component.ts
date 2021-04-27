import {Component, OnInit, ViewChild} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {BookService} from "../../services/book.service";
import {MatTable} from "@angular/material/table";
import {BookDto} from "../../models/book-dto";
import {CommentDto} from "../../models/comment-dto";
import {SharedService} from "../../services/shared.service";

@Component({
    selector: 'comments',
    templateUrl: './comments.component.html',
    styleUrls: [
        './comments.component.css'
    ]
})
export class CommentsComponent implements OnInit {

    private currentBookId: string;
    private currentBook: BookDto;
    public comments: CommentDto[] = [];

    public commentsDisplayedColumns: string[] = ['id', 'commentatorName', 'text', 'actions'];
    @ViewChild('commentsTable') commentsTable: MatTable<any>;

    constructor(private route: ActivatedRoute,
                private bookService: BookService,
                private shared: SharedService) {
    }

    ngOnInit(): void {
        this.currentBookId = this.route.snapshot.paramMap.get('id');
        this.loadBook();
    }

    private loadBook(): void {
        this.bookService.getById(this.currentBookId).subscribe(data => {
            this.currentBook = data;
            this.comments = this.currentBook.comments;
        })
    }

    public cancelCommentEdit(comment: CommentDto): void {
        comment.isEdit = false;
        if (!comment.id) {
            this.comments.splice(this.comments.findIndex(c => c.isEdit), 1);
        }
        this.commentsTable.renderRows();
    }

    public updateComment(comment: CommentDto): void {
        this.bookService.addComment(comment, this.currentBookId).subscribe(data => {
            this.updateComments(comment, this.getNewComment(data));
            comment.isEdit = false;
            this.commentsTable.renderRows();
        })
    }

    private getNewComment(book: BookDto): CommentDto {
        let result = new CommentDto(null, null, null);
        const newComments = book.comments;
        newComments.forEach(item => {
            if (this.currentBook.comments.some(e => e.id === item.id)) {
                // nothing
            } else {
                result = item;
            }
        });
        return result;
    }

    private updateComments(element: CommentDto, book: CommentDto): void {
        let index = this.getElementIndexInCommentsArray(element);

        this.comments.splice(index, 1, book);
    }

    public removeComment(element: CommentDto): void {
        this.bookService.removeComment(element.id, this.currentBookId).subscribe(data => {
            this.removeCommentFromArrayById(element);
        })
    }

    private removeCommentFromArrayById(element: CommentDto): void {
        let index = this.getElementIndexInCommentsArray(element);

        this.comments.splice(index, 1);
        this.commentsTable.renderRows();
    }

    private getElementIndexInCommentsArray(element: CommentDto): number {
        return this.comments.map(function (item) {
            return item.id
        }).indexOf(element.id)
    }

    public newComment(): void {
        let comment = new CommentDto(null, null, null);
        comment.isEdit = true;
        this.comments.push(comment);
        this.commentsTable.renderRows();
    }

    public isCommentReadyToUpdate(element: CommentDto): boolean {
        return !this.shared.isBlank(element.text);
    }

}