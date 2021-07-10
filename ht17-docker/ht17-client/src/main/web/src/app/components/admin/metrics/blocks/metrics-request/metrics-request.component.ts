import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {HttpServerRequests} from "../../metrics.model";
import {filterNaN} from '../../../utils/operators';

@Component({
    selector: 'jhi-metrics-request',
    templateUrl: './metrics-request.component.html',
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class MetricsRequestComponent {
    /**
     * object containing http request related metrics
     */
    @Input() requestMetrics?: HttpServerRequests;

    /**
     * boolean field saying if the metrics are in the process of being updated
     */
    @Input() updating?: boolean;

    public filterNaN = (input: number): number => filterNaN(input);
}
