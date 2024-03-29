import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatMenuModule} from '@angular/material/menu';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatInputModule} from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import {MatDialogModule} from '@angular/material/dialog';
import {MatListModule} from '@angular/material/list';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatCardModule} from '@angular/material/card';
import {MatTabsModule} from '@angular/material/tabs';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatSelectModule} from '@angular/material/select';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatExpansionModule} from '@angular/material/expansion';
import {ReactiveFormsModule} from '@angular/forms';
import {MatTableModule} from '@angular/material/table';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {NgxMaterialTimepickerModule} from 'ngx-material-timepicker';
import {NgbProgressbarModule} from '@ng-bootstrap/ng-bootstrap';


@NgModule({
    imports: [
        CommonModule,
        MatMenuModule,
        MatButtonModule,
        MatToolbarModule,
        MatInputModule,
        MatIconModule,
        MatFormFieldModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatDialogModule,
        MatListModule,
        MatSidenavModule,
        MatCardModule,
        MatProgressBarModule,
        MatTabsModule,
        MatPaginatorModule,
        MatCheckboxModule,
        MatSelectModule,
        MatGridListModule,
        MatExpansionModule,
        ReactiveFormsModule,
        MatTableModule,
        MatSnackBarModule,
        NgxMaterialTimepickerModule,
        NgbProgressbarModule
    ],
    declarations: [],
    providers: [
        MatDatepickerModule
    ],
    exports: [
        MatMenuModule,
        MatButtonModule,
        MatToolbarModule,
        MatInputModule,
        MatIconModule,
        MatFormFieldModule,
        MatDatepickerModule,
        MatDialogModule,
        MatListModule,
        MatSidenavModule,
        MatCardModule,
        MatProgressBarModule,
        MatTabsModule,
        MatPaginatorModule,
        MatCheckboxModule,
        MatSelectModule,
        MatGridListModule,
        MatExpansionModule,
        ReactiveFormsModule,
        MatTableModule,
        MatSnackBarModule,
        NgxMaterialTimepickerModule,
        NgbProgressbarModule
    ]
})
export class MainUiModule {
}
