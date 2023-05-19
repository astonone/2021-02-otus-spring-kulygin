import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {TemplateDto} from "../../models/template-dto";
import {TemplateService} from "../../services/template-service";
import {PageEvent} from "@angular/material/paginator";
import {InterviewTemplateCriteriaDto} from "../../models/interview-template-criteria-dto";
import {MatTableDataSource} from "@angular/material/table";
import {InterviewTemplateCriteriaService} from "../../services/interview-template-criteria-service";
import {TranslateService} from "@ngx-translate/core";
import {SharedService} from "../../services/shared-service";

@Component({
    selector: 'edit-criterias',
    templateUrl: './edit-criterias.component.html',
    styleUrls: [
        './edit-criterias.component.css'
    ]
})

export class EditCriteriasComponent implements OnInit {

    private currentTemplateId: string;
    private template: TemplateDto;

    public totalSize: number = 0;
    public totalPageSize: number = 0;
    public currentPageSize: number = 0;
    public page: number = 0;
    public pageSize: number = 10;
    public pageSizeOptions: number[] = [5, 10, 25, 100];

    // MatPaginator Output
    public pageEvent: PageEvent;

    public displayedColumns: string[] = ['name', 'positionType', 'actions'];
    public globalCriteriaDataSource: MatTableDataSource<InterviewTemplateCriteriaDto> = new MatTableDataSource([]);
    public templateCriteriaDataSource: InterviewTemplateCriteriaDto[] = [];

    constructor(private templateService: TemplateService,
                private interviewTemplateCriteriaService: InterviewTemplateCriteriaService,
                private route: ActivatedRoute,
                private sharedService: SharedService,
                private translateService: TranslateService) {
    }

    ngOnInit(): void {
        this.loadCriterias(this.page, this.pageSize);
        this.currentTemplateId = this.route.snapshot.paramMap.get('id');
        this.templateService.getById(this.currentTemplateId).subscribe(data => {
            this.template = data;
            this.templateCriteriaDataSource = this.template.criterias;
        })
    }

    private loadCriterias(page: number, pageSize: number): void {
        this.interviewTemplateCriteriaService.getAll(page, pageSize).subscribe(data => {
            this.totalSize = data.totalSize;
            this.totalPageSize = data.totalPageSize;
            this.currentPageSize = data.currentPageSize;
            this.page = data.page;
            this.pageSize = data.pageSize;
            this.globalCriteriaDataSource = new MatTableDataSource<InterviewTemplateCriteriaDto>(data.interviewTemplateCriterias);
        }, error => {
            if (error.status === 403) {
                this.translateService.get('snackbar.rights').subscribe(t => {
                    this.sharedService.openSnackBar(t)
                });
            } else {
                this.sharedService.openSnackBar(error.error.message);
            }
        });
    }

    public updatePage($event: PageEvent): PageEvent {
        this.loadCriterias($event.pageIndex, $event.pageSize);
        return $event;
    }

    public applyFilter($event: KeyboardEvent) {
        const filterValue = ($event.target as HTMLInputElement).value;
        this.globalCriteriaDataSource.filter = filterValue.trim().toLowerCase();
    }

    public removeCriteria(element: InterviewTemplateCriteriaDto) {
        this.templateService.removeCriteria(this.currentTemplateId, element.id).subscribe(data => {
            this.templateCriteriaDataSource = data.criterias;
            this.translateService.get('edit-criterias.removed').subscribe(text => {
                this.sharedService.openSnackBar(text);
            });
        }, error => {
            if (error.status === 403) {
                let text = "";
                this.translateService.get('snackbar.rights').subscribe(t => text = t);
                this.sharedService.openSnackBar(text);
            } else {
                this.sharedService.openSnackBar(error.error.message);
            }
        })
    }

    public addCriteria(element: InterviewTemplateCriteriaDto) {
        this.templateService.addCriteria(this.currentTemplateId, element).subscribe(data => {
            this.templateCriteriaDataSource = data.criterias;
            this.translateService.get('edit-criterias.added').subscribe(text => {
                this.sharedService.openSnackBar(text);
            });
        }, error => {
            if (error.status === 403) {
                let text = "";
                this.translateService.get('snackbar.rights').subscribe(t => text = t);
                this.sharedService.openSnackBar(text);
            } else {
                this.sharedService.openSnackBar(error.error.message);
            }
        })
    }

}