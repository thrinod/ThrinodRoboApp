import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITickStock } from '../tick-stock.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../tick-stock.test-samples';

import { TickStockService } from './tick-stock.service';

const requireRestSample: ITickStock = {
  ...sampleWithRequiredData,
};

describe('TickStock Service', () => {
  let service: TickStockService;
  let httpMock: HttpTestingController;
  let expectedResult: ITickStock | ITickStock[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TickStockService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a TickStock', () => {
      const tickStock = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tickStock).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TickStock', () => {
      const tickStock = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tickStock).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TickStock', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TickStock', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TickStock', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTickStockToCollectionIfMissing', () => {
      it('should add a TickStock to an empty array', () => {
        const tickStock: ITickStock = sampleWithRequiredData;
        expectedResult = service.addTickStockToCollectionIfMissing([], tickStock);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tickStock);
      });

      it('should not add a TickStock to an array that contains it', () => {
        const tickStock: ITickStock = sampleWithRequiredData;
        const tickStockCollection: ITickStock[] = [
          {
            ...tickStock,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTickStockToCollectionIfMissing(tickStockCollection, tickStock);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TickStock to an array that doesn't contain it", () => {
        const tickStock: ITickStock = sampleWithRequiredData;
        const tickStockCollection: ITickStock[] = [sampleWithPartialData];
        expectedResult = service.addTickStockToCollectionIfMissing(tickStockCollection, tickStock);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tickStock);
      });

      it('should add only unique TickStock to an array', () => {
        const tickStockArray: ITickStock[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const tickStockCollection: ITickStock[] = [sampleWithRequiredData];
        expectedResult = service.addTickStockToCollectionIfMissing(tickStockCollection, ...tickStockArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tickStock: ITickStock = sampleWithRequiredData;
        const tickStock2: ITickStock = sampleWithPartialData;
        expectedResult = service.addTickStockToCollectionIfMissing([], tickStock, tickStock2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tickStock);
        expect(expectedResult).toContain(tickStock2);
      });

      it('should accept null and undefined values', () => {
        const tickStock: ITickStock = sampleWithRequiredData;
        expectedResult = service.addTickStockToCollectionIfMissing([], null, tickStock, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tickStock);
      });

      it('should return initial array if no TickStock is added', () => {
        const tickStockCollection: ITickStock[] = [sampleWithRequiredData];
        expectedResult = service.addTickStockToCollectionIfMissing(tickStockCollection, undefined, null);
        expect(expectedResult).toEqual(tickStockCollection);
      });
    });

    describe('compareTickStock', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTickStock(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTickStock(entity1, entity2);
        const compareResult2 = service.compareTickStock(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTickStock(entity1, entity2);
        const compareResult2 = service.compareTickStock(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTickStock(entity1, entity2);
        const compareResult2 = service.compareTickStock(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
