import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITickIndex } from '../tick-index.model';
import { TickIndexService } from '../service/tick-index.service';

@Component({
  standalone: true,
  templateUrl: './tick-index-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TickIndexDeleteDialogComponent {
  tickIndex?: ITickIndex;

  constructor(
    protected tickIndexService: TickIndexService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tickIndexService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
