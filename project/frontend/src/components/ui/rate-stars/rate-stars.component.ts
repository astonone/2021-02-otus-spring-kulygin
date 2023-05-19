import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {InterviewTemplateCriteriaDto} from "../../../models/interview-template-criteria-dto";
import {InterviewService} from "../../../services/interview-service";

@Component({
    selector: 'rate-stars',
    templateUrl: './rate-stars.component.html',
    styleUrls: [
        './rate-stars.component.css'
    ]
})

export class RateStarsComponent implements OnInit {

    @Input() criteria: InterviewTemplateCriteriaDto = new InterviewTemplateCriteriaDto(null, null, null, 0, null);
    @Input() interviewId: string;
    @Output() totalMark = new EventEmitter<string>();
    @Output() criteriaMark = new EventEmitter<string>();

    public selectedRating: number;
    public stars = [
        {
            id: 1,
            icon: 'star',
            class: 'star-gray star-hover star'
        },
        {
            id: 2,
            icon: 'star',
            class: 'star-gray star-hover star'
        },
        {
            id: 3,
            icon: 'star',
            class: 'star-gray star-hover star'
        },
        {
            id: 4,
            icon: 'star',
            class: 'star-gray star-hover star'
        },
        {
            id: 5,
            icon: 'star',
            class: 'star-gray star-hover star'
        }
    ];

    constructor(private interviewService: InterviewService) {
    }

    ngOnInit(): void {
        this.selectedRating = this.criteria.mark;
        this.stars.forEach((star) => {
            if (star.id <= this.criteria.mark) {
                star.class = 'star-gold star';
            } else {
                star.class = 'star-gray star';
            }
            return star;
        });
    }

    public selectStar(value): void {
        // prevent multiple selection
        if (this.selectedRating === 0) {
            this.stars.filter((star) => {
                if (star.id <= value) {
                    star.class = 'star-gold star';
                } else {
                    star.class = 'star-gray star';
                }
                return star;
            });
            this.selectedRating = value;
            this.interviewService.updateCriteria(this.interviewId, this.criteria.id, this.selectedRating).subscribe(data => {
                this.totalMark.emit(data.totalMark.toString());
                let criterias = data.interviewTemplate.criterias.filter(criteria => criteria.id === this.criteria.id);
                if (criterias.length > 0) {
                    this.criteriaMark.emit(criterias[0].mark.toString());
                }
            });
        }
    }
}