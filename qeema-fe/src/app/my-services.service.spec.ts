import { TestBed } from '@angular/core/testing';

import { MyServicesService } from './my-services.service';

describe('MyServicesService', () => {
  let service: MyServicesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MyServicesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
