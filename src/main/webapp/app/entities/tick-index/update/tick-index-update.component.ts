import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITickIndex } from '../tick-index.model';
import { TickIndexService } from '../service/tick-index.service';
import { TickIndexFormService, TickIndexFormGroup } from './tick-index-form.service';

@Component({
  standalone: true,
  selector: 'jhi-tick-index-update',
  templateUrl: './tick-index-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TickIndexUpdateComponent implements OnInit {
  isSaving = false;
  tickIndex: ITickIndex | null = null;

  editForm: TickIndexFormGroup = this.tickIndexFormService.createTickIndexFormGroup();

  constructor(
    protected tickIndexService: TickIndexService,
    protected tickIndexFormService: TickIndexFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tickIndex }) => {
      this.tickIndex = tickIndex;
      if (tickIndex) {
        this.updateForm(tickIndex);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tickIndex = this.tickIndexFormService.getTickIndex(this.editForm);
    if (tickIndex.id !== null) {
      this.subscribeToSaveResponse(this.tickIndexService.update(tickIndex));
    } else {
      this.subscribeToSaveResponse(this.tickIndexService.create(tickIndex));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITickIndex>>): void {
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

  protected updateForm(tickIndex: ITickIndex): void {
    this.tickIndex = tickIndex;
    this.tickIndexFormService.resetForm(this.editForm, tickIndex);
  }
}
