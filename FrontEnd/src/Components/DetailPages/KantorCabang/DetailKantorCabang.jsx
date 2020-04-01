import React from 'react';
import classes from './DetailKantorCabang.module.css';
import SirioDetailPage from '../SirioDetailPage';
import SirioButton from '../../Button/SirioButton';

export default class TabelKantorCabang extends React.Component {
    
    columns = [{
        dataField: 'informasi',
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'left' };
        }

    }, {
        dataField: 'isiInformasi',
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'left' };
        }
    }];

    data = [
        { "informasi": "Nama Point  :", "isiInformasi": "Kantor Cabang 1" },
        { "informasi": "Branch Manager  :", "isiInformasi": "Billa" },
        { "informasi": "Area    :", "isiInformasi": "Area 1" },
        { "informasi": "Regional    :", "isiInformasi": "Regional 1" },
        { "informasi": "Jumlah Kunjungan:", "isiInformasi": "2" },
        { "informasi": "Kunjungan Audit:", "isiInformasi": "Sudah Pernah" },
    ]

    // deleteButton() {
    //     return (
    //         <SirioButton purple>
    //             Hapus
    //             className={classes.buttons}
    //         </SirioButton>
    //     )
    // }

    render() {
        return (
            <div>
                <SirioDetailPage
                    title="Detail Kantor Cabang"
                    data={this.data}
                    id='id'
                    columnsDefinition={this.columns}
                    deleteButton={this.deleteButton}
                />
                <SirioButton purple>
                    Hapus
                    className={classes.buttons}
                </SirioButton>
            </div>
        
           
        );
    }
} 