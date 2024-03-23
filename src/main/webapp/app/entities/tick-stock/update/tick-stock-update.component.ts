import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITickStock } from '../tick-stock.model';
import { TickStockService } from '../service/tick-stock.service';
import { TickStockFormService, TickStockFormGroup } from './tick-stock-form.service';

@Component({
  standalone: true,
  selector: 'jhi-tick-stock-update',
  templateUrl: './tick-stock-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TickStockUpdateComponent implements OnInit {
  isSaving = false;
  tickStock: ITickStock | null = null;

  editForm: TickStockFormGroup = this.tickStockFormService.createTickStockFormGroup();

  constructor(
    protected tickStockService: TickStockService,
    protected tickStockFormService: TickStockFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tickStock }) => {
      this.tickStock = tickStock;
      if (tickStock) {
        this.updateForm(tickStock);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tickStock = this.tickStockFormService.getTickStock(this.editForm);
    if (tickStock.id !== null) {
      this.subscribeToSaveResponse(this.tickStockService.update(tickStock));
    } else {
      this.subscribeToSaveResponse(this.tickStockService.create(tickStock));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITickStock>>): void {
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

  protected updateForm(tickStock: ITickStock): void {
    this.tickStock = tickStock;
    this.tickStockFormService.resetForm(this.editForm, tickStock);
  }
}
