import { CdkScrollable } from '@angular/cdk/scrolling';
import {
  I18nPluralPipe,
  NgClass,
  NgFor,
  NgIf,
  PercentPipe,
} from '@angular/common';
import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  OnDestroy,
  OnInit,
  ViewEncapsulation,
} from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatOptionModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSelectChange, MatSelectModule } from '@angular/material/select';
import {
  MatSlideToggleChange,
  MatSlideToggleModule,
} from '@angular/material/slide-toggle';
import { MatTooltipModule } from '@angular/material/tooltip';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FuseFindByKeyPipe } from 'app/shared/helpers/find-by-key.pipe';
import { MainService } from '../../../core/services/main.service';
import {Category, Course, IResultProps, Mall} from '../../../core/models/main.model';
import {BehaviorSubject, catchError, combineLatest, Observable, Subject, takeUntil} from 'rxjs';
import {MallsService} from "../../../core/services/mall.service";

@Component({
  selector: 'main-list',
  templateUrl: './list.component.html',
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
  standalone: true,
  imports: [
    CdkScrollable,
    MatFormFieldModule,
    MatSelectModule,
    MatOptionModule,
    NgFor,
    MatIconModule,
    MatInputModule,
    MatSlideToggleModule,
    NgIf,
    NgClass,
    MatTooltipModule,
    MatProgressBarModule,
    MatButtonModule,
    RouterLink,
    FuseFindByKeyPipe,
    PercentPipe,
    I18nPluralPipe,
  ],
})
export class MainListComponent implements OnInit, OnDestroy {

  malls: Mall[];

  private _unsubscribeAll: Subject<any> = new Subject<any>();
  /**
   * Constructor
   */
  constructor(
    private _activatedRoute: ActivatedRoute,
    private _changeDetectorRef: ChangeDetectorRef,
    private _router: Router,
    private _mallService: MallsService,
  ) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Lifecycle hooks
  // -----------------------------------------------------------------------------------------------------

  /**
   * On init
   */
  ngOnInit(): void {
    this._mallService.getMalls().subscribe(
      value => {this.malls = value; this._changeDetectorRef.detectChanges();}
      );
    // this._testService.getAllInformation()
    // .pipe(takeUntil(this._unsubscribeAll))
    // .subscribe(res => {
    //     this.result = res;
    //     this._changeDetectorRef.detectChanges();
    // });

    // Get the categories


  }

  /**
   * On destroy
   */
  ngOnDestroy(): void {
    // Unsubscribe from all subscriptions
    this._unsubscribeAll.next(null);
    this._unsubscribeAll.complete();
  }

}
