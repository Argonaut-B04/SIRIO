import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from './TabelSOP.module.css';
import SirioTable from '../SirioTable';
import SopService from '../../../Services/SopService';
import { NavLink } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import SirioMessageButton from "../../Button/ActionButton/SirioMessageButton";

/**
 * Kelas untuk membuat komponen tabel sop
 */
class TabelSOP extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rowList: [],
            openNotification: true
        };

        this.renderRows = this.renderRows.bind(this);
        this.endNotification = this.endNotification.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    endNotification() {
        this.setState({
            openNotification: false
        })
    }

    async renderRows() {
        const response = await SopService.getSopList();

        this.setState({
            rowList: response.data.result
        })
    }

    columns = [{
        dataField: 'judul',
        text: 'JUDUL',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "35%", textAlign: 'left' };
        }
    },{
        dataField: 'kategori',
        text: 'KATEGORI',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "35%", textAlign: 'left' }
        },
        
    },{
        dataField: 'noData1',
        text: 'AKSI',
        headerClasses: classes.colheader,
        classes: classes.rowItem,
        style: () => {
            return { textAlign: 'center' }
        },
        formatter: this.getButtonsFirst
    }];

    defaultSorted = [{
        dataField: 'id',
        order: 'asc'
    }];

    headerButton() {
        return (
            <NavLink to={{
                pathname: "/sop/tambah"
            }}>
                <SirioButton
                    purple recommended
                >
                    Tambah SOP
                </SirioButton>
            </NavLink>
        )
    }

    getButtonsFirst(cell, row) {
        return (
            <NavLink to={{
                pathname: "/sop/detail",
                state: {
                    id: row.idSop
                }
            }}>
                <SirioButton
                    purple
                >
                    Detail
                </SirioButton>
            </NavLink>
        )
    }

    render() {
        return (
            <>
                <SirioTable
                    title="Daftar SOP"
                    data={this.state.rowList}
                    id='id'
                    columnsDefinition={this.columns}
                    includeSearchBar
                    headerButton={this.headerButton()}
                />
                {this.props.location.state && this.props.location.state.addSuccess && this.state.openNotification &&
                <SirioMessageButton
                    show
                    classes="d-none"
                    modalTitle="SOP berhasil Disimpan"
                    customConfirmText="Tutup"
                    onClick={this.endNotification}
                />
                }
                {this.props.location.state && this.props.location.state.deleteSuccess && this.state.openNotification &&
                <SirioMessageButton
                    show
                    classes="d-none"
                    modalTitle="SOP berhasil Dihapus"
                    customConfirmText="Tutup"
                    onClick={this.endNotification}
                />
                }
                {this.props.location.state && this.props.location.state.editSuccess && this.state.openNotification &&
                <SirioMessageButton
                    show
                    classes="d-none"
                    modalTitle="SOP berhasil Diubah"
                    customConfirmText="Tutup"
                    onClick={this.endNotification}
                />
                }
            </>
        );
    }
} 

export default withRouter(TabelSOP);