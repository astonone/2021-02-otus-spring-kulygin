import {Component, OnInit} from '@angular/core';
import {PageEvent} from "@angular/material/paginator";
import {TemplateDto} from "../../models/template-dto";
import {TemplateService} from "../../services/template-service";
import {Router} from "@angular/router";
import {SharedService} from "../../services/shared-service";
import {TranslateService} from "@ngx-translate/core";

@Component({
    selector: 'templates',
    templateUrl: './templates.component.html',
    styleUrls: [
        './templates.component.css'
    ]
})

export class TemplatesComponent implements OnInit {

    public totalSize: number = 0;
    public totalPageSize: number = 0;
    public currentPageSize: number = 0;
    public page: number = 0;
    public pageSize: number = 10;
    public pageSizeOptions: number[] = [5, 10, 25, 100];

    // MatPaginator Output
    public pageEvent: PageEvent;

    public dataSource: TemplateDto[] = [];

    public newTemplate: TemplateDto = new TemplateDto(null, null, null);
    public backupTemplate: TemplateDto = new TemplateDto(null, null, null);

    constructor(private templateService: TemplateService,
                private router: Router,
                private sharedService: SharedService,
                private translateService: TranslateService) {
    }

    ngOnInit(): void {
        this.loadTemplates(this.page, this.pageSize);
    }

    private loadTemplates(page: number, pageSize: number): void {
        this.templateService.getAllPageable(page, pageSize).subscribe(data => {
            this.totalSize = data.totalSize;
            this.totalPageSize = data.totalPageSize;
            this.currentPageSize = data.currentPageSize;
            this.page = data.page;
            this.pageSize = data.pageSize;
            this.dataSource = data.templates;
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
        if (this.totalSize > this.currentPageSize) {
            this.loadTemplates($event.pageIndex, $event.pageSize);
        }
        return $event;
    }

    public makeEdit(element: TemplateDto): void {
        this.cancelEditingOtherElements();
        element.isEdit = true;
        this.newTemplate = TemplateDto.createNewObjectFromDto(element);
        this.backupTemplate = TemplateDto.createNewObjectFromDto(element);
    }

    public remove(element: TemplateDto): void {
        this.templateService.removeById(element.id).subscribe(() => {
            this.removeFromDataSourceById(element);
            if (element.id) {
                this.currentPageSize--;
                this.totalSize--;
            }
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

    private removeFromDataSourceById(element: TemplateDto): void {
        let index = this.getElementIndexInDataSource(element);

        this.dataSource.splice(index, 1);
    }

    public isReadyToUpdate(): boolean {
        return !SharedService.isBlank(this.newTemplate.positionName);
    }

    public cancelEdit(element: TemplateDto): void {
        element.isEdit = false;
        if (!element.id) {
            this.dataSource.splice(this.dataSource.findIndex(e => e === element), 1);
        } else {
            element.positionName = this.backupTemplate.positionName;
            this.newTemplate = new TemplateDto(null, null, null);
            this.backupTemplate = new TemplateDto(null, null, null);
        }
    }

    public update(element: TemplateDto): void {
        this.templateService.save(this.newTemplate).subscribe(data => {
            this.newTemplate = new TemplateDto(null, null, null);
            this.backupTemplate = new TemplateDto(null, null, null);
            if (this.dataSource.length + 1 <= this.currentPageSize) {
                this.updateDataSource(element, data);
                element.isEdit = false;
                this.totalSize++;
                this.currentPageSize++;
            } else {
                this.loadTemplates(this.page, this.pageSize);
            }
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

    private updateDataSource(element: any, interviewer: TemplateDto): void {
        let index = this.getElementIndexInDataSource(element);

        this.dataSource.splice(index, 1, interviewer);
    }

    private getElementIndexInDataSource(element: TemplateDto) {
        return this.dataSource.map(function (item) {
            return item.id
        }).indexOf(element.id)
    }

    public create(): void {
        this.cancelEditingOtherElements();
        let template = new TemplateDto(null, null, null);
        template.isEdit = true;
        template.isNew = true;
        this.dataSource.push(template);
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

    public openEditCriterias(element: TemplateDto) {
        this.goto('edit-criterias/' + element.id);
    }

    private goto(route: string): void {
        this.router.navigate([this.translateService.getDefaultLang() + '/' + route]);
    }
}