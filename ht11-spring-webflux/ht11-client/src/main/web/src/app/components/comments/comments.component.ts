import {Component, OnInit, ViewChild} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {BookService} from "../../services/book.service";
import {MatTable} from "@angular/material/table";
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
        this.bookService.getCommentsByBookId(this.currentBookId).subscribe(data => {
            this.comments = data.comments;
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
            this.updateComments(comment, data);
            comment.isEdit = false;
            this.commentsTable.renderRows();
        })
    }

    private updateComments(element: CommentDto, commentDto: CommentDto): void {
        let index = this.getElementIndexInCommentsArray(element);

        this.comments.splice(index, 1, commentDto);
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