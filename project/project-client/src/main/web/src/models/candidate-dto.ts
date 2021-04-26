export class CandidateDto {

    private _id: string;
    private _firstName: string;
    private _lastName: string;
    private _claimingPosition: string;
    private _interviewerComment: string;
    private _cvFile: string;
    private _isEdit: boolean = false;
    private _isNew: boolean = false;

    public static createNewObjectFromDto(candidateDto: CandidateDto): CandidateDto {
        return new CandidateDto(candidateDto.id, candidateDto.firstName, candidateDto.lastName, candidateDto.claimingPosition, candidateDto.interviewerComment, candidateDto.cvFile);
    }

    constructor(id: string, firstName: string, lastName: string, claimingPosition: string, interviewerComment: string, cvFile: string) {
        this._id = id;
        this._firstName = firstName;
        this._lastName = lastName;
        this._claimingPosition = claimingPosition;
        this._interviewerComment = interviewerComment;
        this._cvFile = cvFile;
    }

    get id(): string {
        return this._id;
    }

    set id(value: string) {
        this._id = value;
    }

    get firstName(): string {
        return this._firstName;
    }

    set firstName(value: string) {
        this._firstName = value;
    }

    get lastName(): string {
        return this._lastName;
    }

    set lastName(value: string) {
        this._lastName = value;
    }

    get claimingPosition(): string {
        return this._claimingPosition;
    }

    set claimingPosition(value: string) {
        this._claimingPosition = value;
    }

    get interviewerComment(): string {
        return this._interviewerComment;
    }

    set interviewerComment(value: string) {
        this._interviewerComment = value;
    }

    get cvFile(): string {
        return this._cvFile;
    }

    set cvFile(value: string) {
        this._cvFile = value;
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
            firstName: this._firstName,
            lastName: this._lastName,
            claimingPosition: this._claimingPosition,
            interviewerComment: this._interviewerComment
        }
    }
}