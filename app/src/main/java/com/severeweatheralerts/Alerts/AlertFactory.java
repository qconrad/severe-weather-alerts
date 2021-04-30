package com.severeweatheralerts.Alerts;

import com.severeweatheralerts.Alerts.NWS.*;
import com.severeweatheralerts.Alerts.TestAlerts.ExtremePriorityTest;
import com.severeweatheralerts.Alerts.TestAlerts.HighPriorityTest;
import com.severeweatheralerts.Alerts.TestAlerts.LowPriorityTest;
import com.severeweatheralerts.Alerts.TestAlerts.MediumPriorityTest;

public class AlertFactory {
  public Alert getAlert(String name) {
    if (name == null) return new DefaultAlert();
    switch (name) {
      case "911 Telephone Outage Emergency": return new TelephoneOutage();
      case "Administrative Message": return new AdministrativeMessage();
      case "Air Quality Alert": return new AirQualityAlert();
      case "Air Stagnation Advisory": return new AirStagnationAdvisory();
      case "Arroyo And Small Stream Flood Advisory":
      case "Flood Advisory":
      case "Small Stream Flood Advisory":
      case "Urban And Small Stream Flood Advisory": return new FloodAdvisory();
      case "Ashfall Advisory": return new AshfallAdvisory();
      case "Ashfall Warning": return new AshfallWarning();
      case "Avalanche Advisory": return new AvalancheAdvisory();
      case "Avalanche Warning": return new AvalancheWarning();
      case "Avalanche Watch": return new AvalancheWatch();
      case "Beach Hazards Statement": return new BeachHazardsStatement();
      case "Blizzard Warning": return new BlizzardWarning();
      case "Blizzard Watch": return  new BlizzardWatch();
      case "Blowing Dust Advisory": return new BlowingDustAdvisory();
      case "Blowing Dust Warning": return new BlowingDustWarning();
      case "Brisk Wind Advisory": return new BriskWindAdvisory();
      case "Child Abduction Emergency": return new ChildAbductionEmergency();
      case "Civil Danger Warning": return new CivilDangerWarning();
      case "Civil Emergency Message": return new CivilEmergencyMessage();
      case "Coastal Flood Advisory": return new CoastalFloodAdvisory();
      case "Coastal Flood Statement": return new CoastalFloodStatement();
      case "Coastal Flood Warning": return new CoastalFloodWarning();
      case "Coastal Flood Watch": return new CoastalFloodWatch();
      case "Dense Fog Advisory": return new DenseFogAdvisory();
      case "Dense Smoke Advisory": return new DenseSmokeAdvisory();
      case "Dust Advisory": return new DustAdvisory();
      case "Dust Storm Warning": return new DustStormWarning();
      case "Earthquake Warning": return new EarthquakeWarning();
      case "Evacuation - Immediate": return new EvacuationImmediate();
      case "Excessive Heat Warning": return new ExcessiveHeatWarning();
      case "Excessive Heat Watch": return new ExcessiveHeatWatch();
      case "Extreme Cold Warning": return new ExtremeColdWarning();
      case "Extreme Cold Watch": return new ExtremeColdWatch();
      case "Extreme Fire Danger": return new ExtremeFireDanger();
      case "Extreme Wind Warning": return new ExtremeWindWarning();
      case "Fire Warning": return new FireWarning();
      case "Fire Weather Watch": return new FireWeatherWatch();
      case "Flash Flood Statement":
      case "Flash Flood Warning": return new FlashFloodWarning();
      case "Flash Flood Watch": return new FlashFloodWatch();
      case "Flood Statement": return new FloodStatement();
      case "Flood Warning": return new FloodWarning();
      case "Flood Watch": return new FloodWatch();
      case "Freeze Warning": return new FreezeWarning();
      case "Freeze Watch": return new FreezeWatch();
      case "Freezing Fog Advisory": return new FreezingFogAdvisory();
      case "Freezing Rain Advisory": return new FreezingRainAdvisory();
      case "Freezing Spray Advisory": return new FreezingSprayAdvisory();
      case "Frost Advisory": return new FrostAdvisory();
      case "Gale Warning": return new GaleWarning();
      case "Gale Watch": return new GaleWatch();
      case "Hard Freeze Warning": return new HardFreezeWarning();
      case "Hard Freeze Watch": return new HardFreezeWatch();
      case "Hazardous Materials Warning": return new HazardousMaterialsWarning();
      case "Hazardous Seas Warning": return new HazardousSeasWarning();
      case "Hazardous Seas Watch": return new HazardousSeasWatch();
      case "Hazardous Weather Outlook": return new HazardousWeatherOutlook();
      case "Heat Advisory": return new HeatAdvisory();
      case "Heavy Freezing Spray Warning": return new HeavyFreezingSprayWarning();
      case "Heavy Freezing Spray Watch": return new HeavyFreezingSprayWatch();
      case "High Surf Advisory": return new HighSurfAdvisory();
      case "High Surf Warning": return new HighSurfWarning();
      case "High Wind Warning": return new HighWindWarning();
      case "High Wind Watch": return new HighWindWatch();
      case "Hurricane Force Wind Warning": return new HurricaneForceWindWarning();
      case "Hurricane Force Wind Watch": return new HurricaneForceWindWatch();
      case "Hurricane Local Statement":
      case "Tropical Cyclone Statement": return new HurricaneLocalStatement();
      case "Hurricane Warning": return new HurricaneWarning();
      case "Hurricane Watch": return new HurricaneWatch();
      case "Hydrologic Advisory": return new HydrologicAdvisory();
      case "Hydrologic Outlook": return new HydrologicOutlook();
      case "Ice Storm Warning": return new IceStormWarning();
      case "Lake Effect Snow Advisory": return new LakeEffectSnowAdvisory();
      case "Lake Effect Snow Warning": return new LakeEffectSnowWarning();
      case "Lake Effect Snow Watch": return new LakeEffectSnowWatch();
      case "Lake Wind Advisory": return new LakeWindAdvisory();
      case "Lakeshore Flood Advisory": return new LakeshoreFloodAdvisory();
      case "Lakeshore Flood Statement": return new LakeshoreFloodStatement();
      case "Lakeshore Flood Warning": return new LakeshoreFloodWarning();
      case "Lakeshore Flood Watch": return new LakeshoreFloodWatch();
      case "Law Enforcement Warning": return new LawEnforcementWarning();
      case "Local Area Emergency": return new LocalAreaEmergency();
      case "Low Water Advisory": return new LowWaterAdvisory();
      case "Marine Weather Statement": return new MarineWeatherStatement();
      case "Nuclear Power Plant Warning": return new NuclearPowerPlantWarning();
      case "Radiological Hazard Warning": return new RadiologicalHazardWarning();
      case "Red Flag Warning": return new RedFlagWarning();
      case "Rip Current Statement": return new RipCurrentStatement();
      case "Severe Thunderstorm Warning":
      case "Severe Weather Statement": return new SevereThunderstormWarning();
      case "Severe Thunderstorm Watch": return new SevereThunderstormWatch();
      case "Shelter In Place Warning": return new ShelterInPlaceWarning();
      case "Short Term Forecast": return new ShortTermForecast();
      case "Small Craft Advisory":
      case "Small Craft Advisory for Rough Bar":
      case "Small Craft Advisory for Winds":
      case "Small Craft Advisory for Hazardous Seas": return new SmallCraftAdvisory();
      case "Snow Squall Warning": return new SnowSquallWarning();
      case "Special Marine Warning": return new SpecialMarineWarning();
      case "Special Weather Statement": return new SpecialWeatherStatement();
      case "Storm Surge Warning": return new StormSurgeWarning();
      case "Storm Surge Watch": return new StormSurgeWatch();
      case "Storm Warning": return new StormWarning();
      case "Storm Watch": return new StormWatch();
      case "Tornado Warning": return new TornadoWarning();
      case "Tornado Watch": return new TornadoWatch();
      case "Tropical Depression Local Statement": return new TropicalDepressionLocalStatement();
      case "Tropical Storm Local Statement": return new TropicalStormLocalStatement();
      case "Tropical Storm Warning": return new TropicalStormWarning();
      case "Tropical Storm Watch": return new TropicalStormWatch();
      case "Tsunami Advisory": return new TsunamiAdvisory();
      case "Tsunami Warning": return new TsunamiWarning();
      case "Tsunami Watch": return new TsunamiWatch();
      case "Typhoon Local Statement": return new TyphoonLocalStatement();
      case "Typhoon Warning": return new TyphoonWarning();
      case "Typhoon Watch": return new TyphoonWatch();
      case "Volcano Warning": return new VolcanoWarning();
      case "Wind Advisory": return new WindAdvisory();
      case "Wind Chill Advisory": return new WindChillAdvisory();
      case "Wind Chill Warning": return new WindChillWarning();
      case "Wind Chill Watch": return new WindChillWatch();
      case "Winter Storm Warning": return new WinterStormWarning();
      case "Winter Storm Watch": return new WinterStormWatch();
      case "Winter Weather Advisory": return new WinterWeatherAdvisory();
      case "Low Priority Test": return new LowPriorityTest();
      case "Medium Priority Test": return new MediumPriorityTest();
      case "High Priority Test": return new HighPriorityTest();
      case "Extreme Priority Test": return new ExtremePriorityTest();
      default: return new DefaultAlert();
    }
  }
}
