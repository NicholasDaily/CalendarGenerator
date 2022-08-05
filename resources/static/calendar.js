formContainer = document.createElement("div");
form = document.createElement("div");
label = document.createElement("label");
input = document.createElement("input");
button = document.createElement("button");
dayLabel = document.createElement("h2");
colorHolder= document.createElement("div");
inputBackgroundR = document.createElement("input");
inputBackgroundG = document.createElement("input");
inputBackgroundB = document.createElement("input");
inputColorR = document.createElement("input");
inputColorG = document.createElement("input");
inputColorB = document.createElement("input");
dayLabel.innerText = "Adding Item to: ";
dayLabel.innerText += document.getElementById("monthName").innerText + " ";
day = document.createElement("span");
day.id = "eventFormDay";
dayLabel.appendChild(day);
form.appendChild(dayLabel);
label.innerText = "Event description: ";
form.appendChild(label);
input.placeholder = "Work: 09:00 - 17:00";
input.id = "eventInput";
form.appendChild(input);
button.innerText = "add event";
button.id = "eventSubmit";
colorHolder.id = "colorHolder";
form.appendChild(colorHolder);
inputBackgroundR.setAttribute("type", "range");
inputBackgroundR.setAttribute("min", 0);
inputBackgroundR.setAttribute("max", 255);
inputBackgroundG.setAttribute("type", "range");
inputBackgroundG.setAttribute("min", 0);
inputBackgroundG.setAttribute("max", 255);
inputBackgroundB.setAttribute("type", "range");
inputBackgroundB.setAttribute("min", 0);
inputBackgroundB.setAttribute("max", 255);
inputColorR.setAttribute("type", "range");
inputColorR.setAttribute("min", 0);
inputColorR.setAttribute("max", 255);
inputColorG.setAttribute("type", "range");
inputColorG.setAttribute("min", 0);
inputColorG.setAttribute("max", 255);
inputColorB.setAttribute("type", "range");
inputColorB.setAttribute("min", 0);
inputColorB.setAttribute("max", 255);
previewDiv = document.createElement("div");
previewDiv.classList.add("historyColor");
previewDiv.style.float = "none";
form.appendChild(previewDiv);
form.appendChild(document.createElement("br"));
backgroundLabel = document.createElement("label");
backgroundLabel.innerText = "Background RGB: ";
form.appendChild(backgroundLabel);
form.appendChild(document.createElement("br"));
form.appendChild(inputBackgroundR);
form.appendChild(inputBackgroundG);
form.appendChild(inputBackgroundB);
form.appendChild(document.createElement("br"));
foregroundLabel = document.createElement("label");
foregroundLabel.innerText = "Color RGB: ";
form.appendChild(foregroundLabel);
form.appendChild(document.createElement("br"));
form.appendChild(inputColorR);
form.appendChild(inputColorG);
form.appendChild(inputColorB);
form.appendChild(document.createElement("br"));
form.appendChild(button);
form.classList.add("eventForm");
formContainer.id = "eventFormContainer";
formContainer.appendChild(form);
formContainer.classList.add("no-print");
document.querySelector("body").appendChild(formContainer);
formContainer = document.getElementById("eventFormContainer");
formContainer.style.display = "none";

table = document.querySelector("table");

selectedDay = null;
saveColor = true;
colorHolder.onclick = function(e){
	if(e.target.classList.contains("historyColor")){
		selectedColor = e.target;
		 foregroundColor = selectedColor.style.background.split(',');
		 backgroundColor = selectedColor.style.borderColor.split(',');
		 inputColorR.value = parseInt(foregroundColor[0].substring(4));
		 inputColorG.value = parseInt(foregroundColor[1].substring(1));
		 inputColorB.value = parseInt(foregroundColor[2].substring(1, foregroundColor[2].length - 1));
		 inputBackgroundR.value = parseInt(backgroundColor[0].substring(4));
		 inputBackgroundG.value = parseInt(backgroundColor[1].substring(1));
		 inputBackgroundB.value = parseInt(backgroundColor[2].substring(1, backgroundColor[2].length - 1));
		 
		 previewDiv.style.borderColor = "rgb(" + inputBackgroundR.value 
		+ ", " + inputBackgroundG.value + ", " + inputBackgroundB.value + ")";
		previewDiv.style.background = "rgb(" + inputColorR.value 
		+ ", " + inputColorG.value + ", " + inputColorB.value + ")";
		saveColor = false;
	}
}

formContainer.onclick = function(e){
	if(e.target.id == "eventFormContainer"){
		formContainer.style.display = "none";
	}
}

form.onmouseup = function(){
	previewDiv.style.borderColor = "rgb(" + inputBackgroundR.value 
		+ ", " + inputBackgroundG.value + ", " + inputBackgroundB.value + ")";
		previewDiv.style.background = "rgb(" + inputColorR.value 
		+ ", " + inputColorG.value + ", " + inputColorB.value + ")";
}

button.onclick = function(e){
	dayChildren = selectedDay.children;
	appendArea = null;
	for(i = 0; i < dayChildren.length; i++){
		if(dayChildren[i].classList.contains("eventAppendArea")){
			appendArea = dayChildren[i];
		}
	}
	eventHolder = document.createElement("div");
	eventHolder.classList.add("event");
	eventHolder.style.background = "rgb(" + inputBackgroundR.value 
	+ ", " + inputBackgroundG.value + ", " + inputBackgroundB.value + ")";
	eventText = document.createElement("p");
	eventText.classList.add("eventText");
	eventText.innerText = input.value;
	eventText.style.color = "rgb(" + inputColorR.value + ", " + inputColorG.value + 
	", " + inputColorB.value + ")";
	input.value = "";
	removeEvent = document.createElement("p");
	removeEvent.classList.add("remove-event");
	removeEvent.innerText="x";
	eventHolder.appendChild(eventText);
	eventHolder.appendChild(removeEvent);
	appendArea.appendChild(eventHolder);
	formContainer.style.display = "none";
	if(saveColor){
		colorForHistory = document.createElement("div");
		colorForHistory.classList.add("historyColor");
		colorForHistory.style.borderColor = "rgb(" + inputBackgroundR.value 
		+ ", " + inputBackgroundG.value + ", " + inputBackgroundB.value + ")";
		colorForHistory.style.background = "rgb(" + inputColorR.value 
		+ ", " + inputColorG.value + ", " + inputColorB.value + ")";
		colorHolder.appendChild(colorForHistory);
	}
	saveColor = false;

}

form.onclick = function(e){
	if(e.target.tagName == "INPUT" && e.target.type == 'range'){
		saveColor = true;
	}
}

table.onclick = function(e){
	clicked = e.target;
	if(clicked.classList.contains("addEvent")){
		formContainer.style.display = "flex";
		calendarDay = clicked.parentElement;
		selectedDay = calendarDay;
		dayChildren = calendarDay.children;
		for(i = 0; i < dayChildren.length; i++){
			if(dayChildren[i].classList.contains("dayNumber")){
				document.getElementById("eventFormDay").innerText = dayChildren[i].innerText;
			}
		}
	}
	if(clicked.classList.contains("remove-event")){
		clicked.parentElement.remove();
	}
};
