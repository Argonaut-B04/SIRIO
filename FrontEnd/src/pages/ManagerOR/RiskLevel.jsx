import React, { Component } from 'react';
import SirioMainLayout from '../../Layout/SirioMainLayout';
import TableRiskLevel from '../../Components/Tables/RiskLevel/TableRiskLevel';

class RiskLevel extends Component {
    render() {
        return (
            <SirioMainLayout>
                <TableRiskLevel />
            </SirioMainLayout>
        );
    }
}

export default RiskLevel;
