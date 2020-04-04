import React, { Component } from 'react';
import SirioMainLayout from '../../Layout/SirioMainLayout';
import TableRiskRating from '../../Components/Tables/RiskRating/TableRiskRating';

class RiskRating extends Component {
    render() {
        return (
            <SirioMainLayout>
                <TableRiskRating />
            </SirioMainLayout>
        );
    }
}

export default RiskRating;
