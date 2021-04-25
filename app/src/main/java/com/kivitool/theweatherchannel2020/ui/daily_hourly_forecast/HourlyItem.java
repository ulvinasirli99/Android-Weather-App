package com.kivitool.theweatherchannel2020.ui.daily_hourly_forecast;

import java.util.List;

public class HourlyItem{
	private int dt;
	private int pop;
	private double temp;
	private int windDeg;
	private int visibility;
	private double dewPoint;
	private List<WeatherItem> weather;
	private int humidity;
	private double windSpeed;
	private int pressure;
	private int clouds;
	private double feelsLike;
	private Rain rain;

	public int getDt(){
		return dt;
	}

	public int getPop(){
		return pop;
	}

	public double getTemp(){
		return temp;
	}

	public int getWindDeg(){
		return windDeg;
	}

	public int getVisibility(){
		return visibility;
	}

	public double getDewPoint(){
		return dewPoint;
	}

	public List<WeatherItem> getWeather(){
		return weather;
	}

	public int getHumidity(){
		return humidity;
	}

	public double getWindSpeed(){
		return windSpeed;
	}

	public int getPressure(){
		return pressure;
	}

	public int getClouds(){
		return clouds;
	}

	public double getFeelsLike(){
		return feelsLike;
	}

	public Rain getRain(){
		return rain;
	}
}