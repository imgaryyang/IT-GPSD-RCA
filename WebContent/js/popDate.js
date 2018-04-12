function populateDate(dayId,monthId,yearId)
{
	popDay(dayId);
	popMonth(monthId);
	popYear(yearId, "");
}

function popDay(dayId)
{
	for (var i = 1; i <= 31; i++)
	{
		var optn = document.createElement("OPTION");
		optn.text = i;
		optn.value = i;
		//selectbox.options.add(optn);
		document.getElementById(dayId).options.add(optn);
	}
}

function popMonth(monthId)
{
	//var monthNames = {"January","February","March","April","May","June","July","August","September","October","November","December"};
	var monthNames = new Array("January","February","March","April","May","June","July","August","September","October","November","December");
	for (var i = 0; i < monthNames.length; i++)
	{
		var optn = document.createElement("OPTION");
		optn.text = monthNames[i];
		optn.value = monthNames[i];
		//selectbox.options.add(optn);
		document.getElementById(monthId).options.add(optn);
	}
}

function popYear(yearId,selectedVal)
{
	var currentTime = new Date();
	var year = currentTime.getFullYear();
	
	var yearEnd = parseInt(year) + 1;
	var yearStart = parseInt(year) - 60;
	
	for (var i = yearStart; i <= yearEnd; i++)
	{
		var optn = document.createElement("OPTION");
		optn.text = i;
		optn.value = i;
		if(i==selectedVal)
		{
			optn.selected = true;
		}
		//selectbox.options.add(optn);
		document.getElementById(yearId).options.add(optn);
	}
}

function setBindingDate(id)
{		
	var day = document.getElementById(id+"Day").value;
	var month = document.getElementById(id+"Month").value;
	var year = document.getElementById(id+"Year").value;
	
	if(day.trim() == "" || month.trim() == "" || year.trim() == "")
	{
		//alert("No Date");
		document.getElementById(id).value = "";
	}
	else
	{
		document.getElementById(id).value = day+"-"+month+"-"+year;
		//alert(document.getElementById(id).value);
	}
}

function findMonthIndex(monthName)
{
	//var monthNames = {"January","February","March","April","May","June","July","August","September","October","November","December"};
	var monthNames = new Array("January","February","March","April","May","June","July","August","September","October","November","December");
	for (var i = 0; i < monthNames.length; i++)
	{				
		if(monthNames[i]==monthName)
		{
			return i;
		}		
	}
	return null;
}

function daysInMonth(iMonth, iYear)
{
	
	return 32 - new Date(iYear, iMonth, 32).getDate();
}