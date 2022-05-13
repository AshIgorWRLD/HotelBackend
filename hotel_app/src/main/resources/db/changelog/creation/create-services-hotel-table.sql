CREATE TABLE "services_hotels" ("serviceId" uuid NOT NULL, "hotelId" uuid NOT NULL, CONSTRAINT "PK_50a9cf6bb4012a000b2a80ccab5" PRIMARY KEY ("serviceId", "hotelId"));
CREATE INDEX "IDX_fe232df3c680e89c24db903504" ON "services_hotels" ("serviceId");
CREATE INDEX "IDX_89aa81a7c84011fd675049345f" ON "services_hotels" ("hotelId");
