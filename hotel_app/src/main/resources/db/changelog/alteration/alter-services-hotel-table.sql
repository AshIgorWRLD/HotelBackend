ALTER TABLE "services_hotels" ADD CONSTRAINT "FK_fe232df3c680e89c24db903504e" FOREIGN KEY ("serviceId") REFERENCES "services"("id") ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE "services_hotels" ADD CONSTRAINT "FK_89aa81a7c84011fd675049345f0" FOREIGN KEY ("hotelId") REFERENCES "hotels"("id") ON DELETE CASCADE ON UPDATE CASCADE;
