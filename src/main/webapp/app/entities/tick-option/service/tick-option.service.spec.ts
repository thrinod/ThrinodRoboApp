import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITickOption } from '../tick-option.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../tick-option.test-samples';

import { TickOptionService } from './tick-option.service';

const requireRestSample: ITickOption = {
  ...sampleWithRequiredData,
};

describe('TickOption Service', () => {
  let service: TickOptionService;
  let httpMock: HttpTestingController;
  let expectedResult: ITickOption | ITickOption[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TickOptionService);
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

    it('should create a TickOption', () => {
      const tickOption = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tickOption).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TickOption', () => {
      const tickOption = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tickOption).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TickOption', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TickOption', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TickOption', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTickOptionToCollectionIfMissing', () => {
      it('should add a TickOption to an empty array', () => {
        const tickOption: ITickOption = sampleWithRequiredData;
        expectedResult = service.addTickOptionToCollectionIfMissing([], tickOption);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tickOption);
      });

      it('should not add a TickOption to an array that contains it', () => {
        const tickOption: ITickOption = sampleWithRequiredData;
        const tickOptionCollection: ITickOption[] = [
          {
            ...tickOption,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTickOptionToCollectionIfMissing(tickOptionCollection, tickOption);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TickOption to an array that doesn't contain it", () => {
        const tickOption: ITickOption = sampleWithRequiredData;
        const tickOptionCollection: ITickOption[] = [sampleWithPartialData];
        expectedResult = service.addTickOptionToCollectionIfMissing(tickOptionCollection, tickOption);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tickOption);
      });

      it('should add only unique TickOption to an array', () => {
        const tickOptionArray: ITickOption[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const tickOptionCollection: ITickOption[] = [sampleWithRequiredData];
        expectedResult = service.addTickOptionToCollectionIfMissing(tickOptionCollection, ...tickOptionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tickOption: ITickOption = sampleWithRequiredData;
        const tickOption2: ITickOption = sampleWithPartialData;
        expectedResult = service.addTickOptionToCollectionIfMissing([], tickOption, tickOption2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tickOption);
        expect(expectedResult).toContain(tickOption2);
      });

      it('should accept null and undefined values', () => {
        const tickOption: ITickOption = sampleWithRequiredData;
        expectedResult = service.addTickOptionToCollectionIfMissing([], null, tickOption, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tickOption);
      });

      it('should return initial array if no TickOption is added', () => {
        const tickOptionCollection: ITickOption[] = [sampleWithRequiredData];
        expectedResult = service.addTickOptionToCollectionIfMissing(tickOptionCollection, undefined, null);
        expect(expectedResult).toEqual(tickOptionCollection);
      });
    });

    describe('compareTickOption', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTickOption(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTickOption(entity1, entity2);
        const compareResult2 = service.compareTickOption(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTickOption(entity1, entity2);
        const compareResult2 = service.compareTickOption(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTickOption(entity1, entity2);
        const compareResult2 = service.compareTickOption(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
