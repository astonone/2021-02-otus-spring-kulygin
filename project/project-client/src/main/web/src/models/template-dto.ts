import {InterviewTemplateCriteriaDto} from "./interview-template-criteria-dto";

export class TemplateDto {

    private _id: string;
    private _positionName: string;
    private _criterias: InterviewTemplateCriteriaDto[] = [];
    private _isEdit: boolean = false;
    private _isNew: boolean = false;

    public static createNewObjectFromDto(template: TemplateDto): TemplateDto {
        return new TemplateDto(template.id, template.positionName, template.criterias);
    }

    constructor(id: string, positionName: string, criterias: InterviewTemplateCriteriaDto[]) {
        this._id = id;
        this._positionName = positionName;
        this._criterias = criterias;
    }

    get id(): string {
        return this._id;
    }

    set id(value: string) {
        this._id = value;
    }

    get positionName(): string {
        return this._positionName;
    }

    set positionName(value: string) {
        this._positionName = value;
    }

    get criterias(): InterviewTemplateCriteriaDto[] {
        return this._criterias;
    }

    set criterias(value: InterviewTemplateCriteriaDto[]) {
        this._criterias = value;
    }

    get isEdit(): boolean {
        return this._isEdit;
    }

    set isEdit(value: boolean) {
        this._isEdit = value;
    }

    get isNew(): boolean {
        return this._isNew;
    }

    set isNew(value: boolean) {
        this._isNew = value;
    }

    public toObject(): any {
        return {
            id: this._id,
            positionName: this._positionName
        }
    }

}