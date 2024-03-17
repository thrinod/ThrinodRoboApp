import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITICK } from '../tick.model';
import { TICKService } from '../service/tick.service';
import { TICKFormService, TICKFormGroup } from './tick-form.service';

@Component({
  standalone: true,
  selector: 'jhi-tick-update',
  templateUrl: './tick-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TICKUpdateComponent implements OnInit {
  isSaving = false;
  tICK: ITICK | null = null;

  editForm: TICKFormGroup = this.tICKFormService.createTICKFormGroup();

  constructor(
    protected tICKService: TICKService,
    protected tICKFormService: TICKFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tICK }) => {
      this.tICK = tICK;
      if (tICK) {
        this.updateForm(tICK);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tICK = this.tICKFormService.getTICK(this.editForm);
    if (tICK.id !== null) {
      this.subscribeToSaveResponse(this.tICKService.update(tICK));
    } else {
      this.subscribeToSaveResponse(this.tICKService.create(tICK));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITICK>>): void {
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

  protected updateForm(tICK: ITICK): void {
    this.tICK = tICK;
    this.tICKFormService.resetForm(this.editForm, tICK);
  }
}
