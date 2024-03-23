import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITickStock } from '../tick-stock.model';
import { TickStockService } from '../service/tick-stock.service';

@Component({
  standalone: true,
  templateUrl: './tick-stock-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TickStockDeleteDialogComponent {
  tickStock?: ITickStock;

  constructor(
    protected tickStockService: TickStockService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tickStockService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
