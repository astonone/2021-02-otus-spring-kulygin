import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

import {Health, HealthDetails, HealthStatus} from './health.model';
import {HealthModalComponent} from './modal/health-modal.component';
import {HealthService} from "../../../services/admin/health.service";

@Component({
    selector: 'health',
    templateUrl: './health.component.html',
    styleUrls: [
        './health.component.css'
    ]
})
export class HealthComponent implements OnInit {
    health?: Health;

    constructor(private modalService: NgbModal, private healthService: HealthService) {
    }

    public ngOnInit(): void {
        this.refresh();
    }

    public getBadgeClass(statusState: HealthStatus): string {
        if (statusState === 'UP') {
            return 'badge-success';
        } else {
            return 'badge-danger';
        }
    }

    public refresh(): void {
        this.healthService.checkHealth().subscribe(
            health => (this.health = health),
            (error: HttpErrorResponse) => {
                if (error.status === 503) {
                    this.health = error.error;
                }
            }
        );
    }

    public showHealth(health: { key: string; value: HealthDetails }): void {
        const modalRef = this.modalService.open(HealthModalComponent);
        modalRef.componentInstance.health = health;
    }
}
