import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITickFutures } from '../tick-futures.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../tick-futures.test-samples';

import { TickFuturesService } from './tick-futures.service';

const requireRestSample: ITickFutures = {
  ...sampleWithRequiredData,
};

describe('TickFutures Service', () => {
  let service: TickFuturesService;
  let httpMock: HttpTestingController;
  let expectedResult: ITickFutures | ITickFutures[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TickFuturesService);
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

    it('should create a TickFutures', () => {
      const tickFutures = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tickFutures).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TickFutures', () => {
      const tickFutures = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tickFutures).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TickFutures', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TickFutures', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TickFutures', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTickFuturesToCollectionIfMissing', () => {
      it('should add a TickFutures to an empty array', () => {
        const tickFutures: ITickFutures = sampleWithRequiredData;
        expectedResult = service.addTickFuturesToCollectionIfMissing([], tickFutures);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tickFutures);
      });

      it('should not add a TickFutures to an array that contains it', () => {
        const tickFutures: ITickFutures = sampleWithRequiredData;
        const tickFuturesCollection: ITickFutures[] = [
          {
            ...tickFutures,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTickFuturesToCollectionIfMissing(tickFuturesCollection, tickFutures);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TickFutures to an array that doesn't contain it", () => {
        const tickFutures: ITickFutures = sampleWithRequiredData;
        const tickFuturesCollection: ITickFutures[] = [sampleWithPartialData];
        expectedResult = service.addTickFuturesToCollectionIfMissing(tickFuturesCollection, tickFutures);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tickFutures);
      });

      it('should add only unique TickFutures to an array', () => {
        const tickFuturesArray: ITickFutures[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const tickFuturesCollection: ITickFutures[] = [sampleWithRequiredData];
        expectedResult = service.addTickFuturesToCollectionIfMissing(tickFuturesCollection, ...tickFuturesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tickFutures: ITickFutures = sampleWithRequiredData;
        const tickFutures2: ITickFutures = sampleWithPartialData;
        expectedResult = service.addTickFuturesToCollectionIfMissing([], tickFutures, tickFutures2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tickFutures);
        expect(expectedResult).toContain(tickFutures2);
      });

      it('should accept null and undefined values', () => {
        const tickFutures: ITickFutures = sampleWithRequiredData;
        expectedResult = service.addTickFuturesToCollectionIfMissing([], null, tickFutures, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tickFutures);
      });

      it('should return initial array if no TickFutures is added', () => {
        const tickFuturesCollection: ITickFutures[] = [sampleWithRequiredData];
        expectedResult = service.addTickFuturesToCollectionIfMissing(tickFuturesCollection, undefined, null);
        expect(expectedResult).toEqual(tickFuturesCollection);
      });
    });

    describe('compareTickFutures', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTickFutures(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTickFutures(entity1, entity2);
        const compareResult2 = service.compareTickFutures(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTickFutures(entity1, entity2);
        const compareResult2 = service.compareTickFutures(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTickFutures(entity1, entity2);
        const compareResult2 = service.compareTickFutures(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
