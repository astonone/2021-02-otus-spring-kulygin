import {Component, OnInit, ViewChild} from '@angular/core';
import {PageEvent} from "@angular/material/paginator";
import {MatTable} from "@angular/material/table";
import {InterviewTemplateCriteriaDto} from "../../models/interview-template-criteria-dto";
import {InterviewTemplateCriteriaService} from "../../services/interview-template-criteria-service";
import {SharedService} from "../../services/shared-service";
import {TranslateService} from "@ngx-translate/core";

@Component({
    selector: 'template-criteria',
    templateUrl: './template-criteria.component.html',
    styleUrls: [
        './template-criteria.component.css'
    ]
})

export class TemplateCriteriaComponent implements OnInit {

    public totalSize: number = 0;
    public totalPageSize: number = 0;
    public currentPageSize: number = 0;
    public page: number = 0;
    public pageSize: number = 10;
    public pageSizeOptions: number[] = [5, 10, 25, 100];

    // MatPaginator Output
    public pageEvent: PageEvent;

    public displayedColumns: string[] = ['name', 'positionType', 'actions'];
    public dataSource: InterviewTemplateCriteriaDto[] = [];

    private newCriteria: InterviewTemplateCriteriaDto = new InterviewTemplateCriteriaDto(null, null, null, null, null);

    @ViewChild('criteriaTable') criteriaTable: MatTable<any>;

    constructor(private interviewTemplateCriteriaService: InterviewTemplateCriteriaService,
                private sharedService: SharedService,
                private translateService: TranslateService) {
    }

    ngOnInit(): void {
        this.loadCriterias(this.page, this.pageSize);
    }

    private loadCriterias(page: number, pageSize: number): void {
        this.interviewTemplateCriteriaService.getAll(page, pageSize).subscribe(data => {
            this.totalSize = data.totalSize;
            this.totalPageSize = data.totalPageSize;
            this.currentPageSize = data.currentPageSize;
            this.page = data.page;
            this.pageSize = data.pageSize;
            this.dataSource = data.interviewTemplateCriterias;
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

    public makeEdit(element: InterviewTemplateCriteriaDto): void {
        this.cancelEditingOtherElements();
        element.isEdit = true;
        this.newCriteria = new InterviewTemplateCriteriaDto(element.id, element.name, element.positionType, null, null);
        this.criteriaTable.renderRows();
    }

    public remove(element: InterviewTemplateCriteriaDto): void {
        this.interviewTemplateCriteriaService.removeById(element.id).subscribe(() => {
            this.removeFromDataSourceById(element);
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

    private removeFromDataSourceById(element: InterviewTemplateCriteriaDto): void {
        let index = this.getElementIndexInDataSource(element);

        this.dataSource.splice(index, 1);
        this.criteriaTable.renderRows();
    }

    public isReadyToUpdate(element: InterviewTemplateCriteriaDto): boolean {
        return !TemplateCriteriaComponent.isBlank(element.name) &&
            !TemplateCriteriaComponent.isBlank(element.positionType);
    }

    private static isBlank(str: string): boolean {
        return (!str || /^\s*$/.test(str));
    }

    public cancelEdit(element: InterviewTemplateCriteriaDto): void {
        element.isEdit = false;
        if (!element.id) {
            this.dataSource.splice(this.dataSource.findIndex(e => e === element), 1);
            this.criteriaTable.renderRows();
        } else {
            element.name = this.newCriteria.name;
            element.positionType = this.newCriteria.positionType;
        }
    }

    public update(element: InterviewTemplateCriteriaDto): void {
        this.interviewTemplateCriteriaService.save(InterviewTemplateCriteriaDto.createNewObjectFromDto(element)).subscribe(data => {
            this.loadCriterias(this.page, this.pageSize);
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

    private getElementIndexInDataSource(element: InterviewTemplateCriteriaDto) {
        return this.dataSource.map(function (item) {
            return item.id
        }).indexOf(element.id)
    }

    public create(): void {
        this.cancelEditingOtherElements();
        let criteria = new InterviewTemplateCriteriaDto(null, null, null, null, null);
        criteria.isEdit = true;
        this.dataSource.push(criteria);
        this.criteriaTable.renderRows();
    }

    private cancelEditingOtherElements(): void {
        function isEditing(element, index, array) {
            return (element.isEdit);
        }

        let filtered = this.dataSource.filter(isEditing);
        if (filtered.length > 0) {
            this.cancelEdit(filtered[0]);
        }
    }

}