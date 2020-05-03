import React, { Component } from 'react';
import SirioMainLayout from "../../Layout/SirioMainLayout";
import ReminderTemplateForm from '../../Components/Form/Reminder/ReminderTemplateForm';

export class TemplateReminder extends Component {
    render() {
        return (
            <SirioMainLayout>
                <ReminderTemplateForm />
            </SirioMainLayout>
        )
    }
}

export default TemplateReminder;