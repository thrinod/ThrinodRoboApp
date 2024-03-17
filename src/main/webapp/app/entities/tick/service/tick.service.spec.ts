import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITICK } from '../tick.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../tick.test-samples';

import { TICKService } from './tick.service';

const requireRestSample: ITICK = {
  ...sampleWithRequiredData,
};

describe('TICK Service', () => {
  let service: TICKService;
  let httpMock: HttpTestingController;
  let expectedResult: ITICK | ITICK[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TICKService);
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

    it('should create a TICK', () => {
      const tICK = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tICK).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TICK', () => {
      const tICK = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tICK).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TICK', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TICK', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TICK', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTICKToCollectionIfMissing', () => {
      it('should add a TICK to an empty array', () => {
        const tICK: ITICK = sampleWithRequiredData;
        expectedResult = service.addTICKToCollectionIfMissing([], tICK);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tICK);
      });

      it('should not add a TICK to an array that contains it', () => {
        const tICK: ITICK = sampleWithRequiredData;
        const tICKCollection: ITICK[] = [
          {
            ...tICK,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTICKToCollectionIfMissing(tICKCollection, tICK);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TICK to an array that doesn't contain it", () => {
        const tICK: ITICK = sampleWithRequiredData;
        const tICKCollection: ITICK[] = [sampleWithPartialData];
        expectedResult = service.addTICKToCollectionIfMissing(tICKCollection, tICK);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tICK);
      });

      it('should add only unique TICK to an array', () => {
        const tICKArray: ITICK[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const tICKCollection: ITICK[] = [sampleWithRequiredData];
        expectedResult = service.addTICKToCollectionIfMissing(tICKCollection, ...tICKArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tICK: ITICK = sampleWithRequiredData;
        const tICK2: ITICK = sampleWithPartialData;
        expectedResult = service.addTICKToCollectionIfMissing([], tICK, tICK2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tICK);
        expect(expectedResult).toContain(tICK2);
      });

      it('should accept null and undefined values', () => {
        const tICK: ITICK = sampleWithRequiredData;
        expectedResult = service.addTICKToCollectionIfMissing([], null, tICK, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tICK);
      });

      it('should return initial array if no TICK is added', () => {
        const tICKCollection: ITICK[] = [sampleWithRequiredData];
        expectedResult = service.addTICKToCollectionIfMissing(tICKCollection, undefined, null);
        expect(expectedResult).toEqual(tICKCollection);
      });
    });

    describe('compareTICK', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTICK(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTICK(entity1, entity2);
        const compareResult2 = service.compareTICK(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTICK(entity1, entity2);
        const compareResult2 = service.compareTICK(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTICK(entity1, entity2);
        const compareResult2 = service.compareTICK(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
