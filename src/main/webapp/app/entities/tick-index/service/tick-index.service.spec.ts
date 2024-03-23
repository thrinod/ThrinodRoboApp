import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITickIndex } from '../tick-index.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../tick-index.test-samples';

import { TickIndexService } from './tick-index.service';

const requireRestSample: ITickIndex = {
  ...sampleWithRequiredData,
};

describe('TickIndex Service', () => {
  let service: TickIndexService;
  let httpMock: HttpTestingController;
  let expectedResult: ITickIndex | ITickIndex[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TickIndexService);
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

    it('should create a TickIndex', () => {
      const tickIndex = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tickIndex).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TickIndex', () => {
      const tickIndex = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tickIndex).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TickIndex', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TickIndex', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TickIndex', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTickIndexToCollectionIfMissing', () => {
      it('should add a TickIndex to an empty array', () => {
        const tickIndex: ITickIndex = sampleWithRequiredData;
        expectedResult = service.addTickIndexToCollectionIfMissing([], tickIndex);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tickIndex);
      });

      it('should not add a TickIndex to an array that contains it', () => {
        const tickIndex: ITickIndex = sampleWithRequiredData;
        const tickIndexCollection: ITickIndex[] = [
          {
            ...tickIndex,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTickIndexToCollectionIfMissing(tickIndexCollection, tickIndex);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TickIndex to an array that doesn't contain it", () => {
        const tickIndex: ITickIndex = sampleWithRequiredData;
        const tickIndexCollection: ITickIndex[] = [sampleWithPartialData];
        expectedResult = service.addTickIndexToCollectionIfMissing(tickIndexCollection, tickIndex);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tickIndex);
      });

      it('should add only unique TickIndex to an array', () => {
        const tickIndexArray: ITickIndex[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const tickIndexCollection: ITickIndex[] = [sampleWithRequiredData];
        expectedResult = service.addTickIndexToCollectionIfMissing(tickIndexCollection, ...tickIndexArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tickIndex: ITickIndex = sampleWithRequiredData;
        const tickIndex2: ITickIndex = sampleWithPartialData;
        expectedResult = service.addTickIndexToCollectionIfMissing([], tickIndex, tickIndex2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tickIndex);
        expect(expectedResult).toContain(tickIndex2);
      });

      it('should accept null and undefined values', () => {
        const tickIndex: ITickIndex = sampleWithRequiredData;
        expectedResult = service.addTickIndexToCollectionIfMissing([], null, tickIndex, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tickIndex);
      });

      it('should return initial array if no TickIndex is added', () => {
        const tickIndexCollection: ITickIndex[] = [sampleWithRequiredData];
        expectedResult = service.addTickIndexToCollectionIfMissing(tickIndexCollection, undefined, null);
        expect(expectedResult).toEqual(tickIndexCollection);
      });
    });

    describe('compareTickIndex', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTickIndex(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTickIndex(entity1, entity2);
        const compareResult2 = service.compareTickIndex(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTickIndex(entity1, entity2);
        const compareResult2 = service.compareTickIndex(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTickIndex(entity1, entity2);
        const compareResult2 = service.compareTickIndex(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
