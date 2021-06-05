import {Injectable} from "@angular/core";
import {MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition} from "@angular/material/snack-bar";
import {TranslateService} from "@ngx-translate/core";

@Injectable({
    providedIn: 'root'
})
export class SharedService {

    private horizontalPosition: MatSnackBarHorizontalPosition = 'end';
    private verticalPosition: MatSnackBarVerticalPosition = 'top';


    constructor(private snackBar: MatSnackBar,
                private translateService: TranslateService) {
    }

    public setTheme(theme: string) {
        localStorage.setItem('theme', theme);
    }

    public getThemeAndApply() {
        const theme = localStorage.getItem('theme');
        const themeElement: any = document.getElementById('themeAsset');
        themeElement.href = '/assets/themes/' + theme + '.css';
    }

    public isMobile = () => screen.width < 768;

    public static isBlank(str: string): boolean {
        return (!str || /^\s*$/.test(str));
    }

    public openSnackBar(snackBarText: string): void {
        let text = '';
        this.translateService.get('snackbar.end-now').subscribe(t => text = t);
        this.snackBar.open(snackBarText, text, {
            duration: 2000,
            horizontalPosition: this.horizontalPosition,
            verticalPosition: this.verticalPosition,
        });
    }

}