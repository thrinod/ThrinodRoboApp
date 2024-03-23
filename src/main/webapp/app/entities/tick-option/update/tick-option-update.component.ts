import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITickOption } from '../tick-option.model';
import { TickOptionService } from '../service/tick-option.service';
import { TickOptionFormService, TickOptionFormGroup } from './tick-option-form.service';

@Component({
  standalone: true,
  selector: 'jhi-tick-option-update',
  templateUrl: './tick-option-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TickOptionUpdateComponent implements OnInit {
  isSaving = false;
  tickOption: ITickOption | null = null;

  editForm: TickOptionFormGroup = this.tickOptionFormService.createTickOptionFormGroup();

  constructor(
    protected tickOptionService: TickOptionService,
    protected tickOptionFormService: TickOptionFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tickOption }) => {
      this.tickOption = tickOption;
      if (tickOption) {
        this.updateForm(tickOption);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tickOption = this.tickOptionFormService.getTickOption(this.editForm);
    if (tickOption.id !== null) {
      this.subscribeToSaveResponse(this.tickOptionService.update(tickOption));
    } else {
      this.subscribeToSaveResponse(this.tickOptionService.create(tickOption));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITickOption>>): void {
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

  protected updateForm(tickOption: ITickOption): void {
    this.tickOption = tickOption;
    this.tickOptionFormService.resetForm(this.editForm, tickOption);
  }
}
