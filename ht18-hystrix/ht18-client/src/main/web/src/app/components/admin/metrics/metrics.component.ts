import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {combineLatest} from 'rxjs';

import {Metrics, Thread} from './metrics.model';
import {MetricsService} from "../../../services/admin/metrics.service";

@Component({
    selector: 'metrics',
    templateUrl: './metrics.component.html',
    styleUrls: [
        './metrics.component.css'
    ],
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class MetricsComponent implements OnInit {
    metrics?: Metrics;
    threads?: Thread[];
    updatingMetrics = true;

    constructor(private metricsService: MetricsService, private changeDetector: ChangeDetectorRef) {
    }

    public ngOnInit(): void {
        this.refresh();
    }

    public refresh(): void {
        this.updatingMetrics = true;
        combineLatest([this.metricsService.getMetrics(), this.metricsService.threadDump()]).subscribe(([metrics, threadDump]) => {
            this.metrics = metrics;
            this.threads = threadDump.threads;
            this.updatingMetrics = false;
            this.changeDetector.markForCheck();
        });
    }

    public metricsKeyExists(key: keyof Metrics): boolean {
        return Boolean(this.metrics?.[key]);
    }

    public metricsKeyExistsAndObjectNotEmpty(key: keyof Metrics): boolean {
        return Boolean(this.metrics?.[key] && JSON.stringify(this.metrics[key]) !== '{}');
    }
}
