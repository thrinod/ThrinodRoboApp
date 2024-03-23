import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITickFutures } from '../tick-futures.model';
import { TickFuturesService } from '../service/tick-futures.service';
import { TickFuturesFormService, TickFuturesFormGroup } from './tick-futures-form.service';

@Component({
  standalone: true,
  selector: 'jhi-tick-futures-update',
  templateUrl: './tick-futures-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TickFuturesUpdateComponent implements OnInit {
  isSaving = false;
  tickFutures: ITickFutures | null = null;

  editForm: TickFuturesFormGroup = this.tickFuturesFormService.createTickFuturesFormGroup();

  constructor(
    protected tickFuturesService: TickFuturesService,
    protected tickFuturesFormService: TickFuturesFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tickFutures }) => {
      this.tickFutures = tickFutures;
      if (tickFutures) {
        this.updateForm(tickFutures);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tickFutures = this.tickFuturesFormService.getTickFutures(this.editForm);
    if (tickFutures.id !== null) {
      this.subscribeToSaveResponse(this.tickFuturesService.update(tickFutures));
    } else {
      this.subscribeToSaveResponse(this.tickFuturesService.create(tickFutures));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITickFutures>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(tickFutures: ITickFutures): void {
    this.tickFutures = tickFutures;
    this.tickFuturesFormService.resetForm(this.editForm, tickFutures);
  }
}
